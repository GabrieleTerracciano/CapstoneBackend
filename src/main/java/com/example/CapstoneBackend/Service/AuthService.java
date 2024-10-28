package com.example.CapstoneBackend.Service;

import com.example.CapstoneBackend.Entity.User;
import com.example.CapstoneBackend.Exception.UnauthorizedException;
import com.example.CapstoneBackend.Exception.UserNotFoundException;
import com.example.CapstoneBackend.Security.JwtTool;
import com.example.CapstoneBackend.dto.UserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTool jwtTool;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticateUserAndCreateToken(UserLoginDTO userLoginDTO) throws UnauthorizedException, UserNotFoundException {
        User user = userService.getUserByEmail(userLoginDTO.getEmail());

        if (user == null) {
            System.out.println("User not found: " + userLoginDTO.getEmail());
            throw new UserNotFoundException("User with email " + userLoginDTO.getEmail() + " not found.");
        }

        System.out.println("User found: " + user.getEmail());
        System.out.println("Provided password: " + userLoginDTO.getPassword());
        System.out.println("Encoded password in DB: " + user.getPassword());

        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            System.out.println("Password does not match");
            throw new UnauthorizedException("Error in authorization, relogin!");
        }

        System.out.println("Password matches");

        return jwtTool.createToken(user);
    }
}
