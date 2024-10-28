package com.example.CapstoneBackend.Service;

import com.example.CapstoneBackend.Entity.User;
import com.example.CapstoneBackend.Exception.EmailAlreadyInUseException;
import com.example.CapstoneBackend.Exception.UserNotFoundException;
import com.example.CapstoneBackend.Exception.EmailNotFoundException;
import com.example.CapstoneBackend.Repository.UserRepository;
import com.example.CapstoneBackend.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String createUser(UserDTO userDTO) throws EmailAlreadyInUseException {
        if (isEmailInUse(userDTO.getEmail())) {
            throw new EmailAlreadyInUseException("Email " + userDTO.getEmail() + " already in use.");
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());

        userRepository.save(user);
        System.out.println("Registration email sent to user: " + user.getEmail());
        System.out.println("User with email " + user.getEmail() + " saved.");
        return "User with email " + user.getEmail() + " saved.";
    }

    private boolean isEmailInUse(String email) {
        try {
            getUserByEmail(email);
            return true;
        } catch (UserNotFoundException e) {
            // Email non trovata per l'utente, continua il controllo
        }
        return false;
    }

    public User getLoggedInUser() throws EmailNotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof User) {
            String email = ((User) principal).getEmail();
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new EmailNotFoundException("User with email " + email + " not found."));
        } else {
            throw new EmailNotFoundException("User is not authenticated");
        }
    }

    public User getUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    public Page<User> getUsers(int page, String sortBy) {
        int fixedSize = 15;
        Pageable pageable = PageRequest.of(page, fixedSize, Sort.by(sortBy));
        Page<User> users = userRepository.findAll(pageable);
        System.out.println("Retrieved users page " + page + " with fixed size " + fixedSize + " sorted by " + sortBy);
        return users;
    }

    public User updateUser(String email, UserDTO userDTO) throws UserNotFoundException {
        User user = getUserByEmail(email);
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        userRepository.save(user);
        System.out.println("User with email " + user.getEmail() + " updated.");
        return user;
    }

    public String deleteUser(String email) throws UserNotFoundException {
        User user = getUserByEmail(email);
        userRepository.delete(user);
        System.out.println("User with email " + email + " deleted successfully.");
        return "User with email " + email + " deleted successfully.";
    }
}
