package com.example.CapstoneBackend.Controller;

import com.example.CapstoneBackend.Entity.User;
import com.example.CapstoneBackend.Enum.Role;
import com.example.CapstoneBackend.Exception.EmailAlreadyInUseException;
import com.example.CapstoneBackend.Exception.EmailNotFoundException;
import com.example.CapstoneBackend.Exception.UnauthorizedException;
import com.example.CapstoneBackend.Exception.UserNotFoundException;
import com.example.CapstoneBackend.Service.AuthService;
import com.example.CapstoneBackend.Service.UserService;
import com.example.CapstoneBackend.dto.UserDTO;
import com.example.CapstoneBackend.dto.UserLoginDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/auth/register")
    public ResponseEntity<Map<String, String>> registerUser(
            @RequestBody @Validated UserDTO userDTO,
            BindingResult bindingResult)
            throws BadRequestException, EmailAlreadyInUseException {

        if (bindingResult.hasErrors()) {
            throw createBadRequestException(bindingResult);
        }

        userDTO.setRole(Role.USER);
        try {
            userService.createUser(userDTO);
            return createSuccessResponse("User with email " + userDTO.getEmail() + " has been created!");
        } catch (EmailAlreadyInUseException e) {
            return createErrorResponse(e.getMessage(), 409);
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestBody @Validated UserLoginDTO userLoginDTO,
            BindingResult bindingResult)
            throws BadRequestException, UserNotFoundException, UnauthorizedException {

        if (bindingResult.hasErrors()) {
            throw createBadRequestException(bindingResult);
        }

        try {
            String token = authService.authenticateUserAndCreateToken(userLoginDTO);
            return createSuccessResponse(token);
        } catch (UserNotFoundException | UnauthorizedException e) {
            return createErrorResponse(e.getMessage(), 401);
        }
    }

    @GetMapping("/auth/user")
    public ResponseEntity<Map<String, String>> getAuthenticatedUser() {
        try {
            User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Map<String, String> response = new HashMap<>();
            response.put("email", loggedInUser.getEmail());
            response.put("name", loggedInUser.getName());
            response.put("surname", loggedInUser.getSurname());
            response.put("role", loggedInUser.getRole().toString());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return createErrorResponse("User not authenticated", 401);
        }
    }

    private BadRequestException createBadRequestException(BindingResult bindingResult) {
        String errorMessage = bindingResult.getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .reduce("", String::concat);
        return new BadRequestException(errorMessage);
    }

    private ResponseEntity<Map<String, String>> createSuccessResponse(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<Map<String, String>> createErrorResponse(String errorMessage, int status) {
        Map<String, String> response = new HashMap<>();
        response.put("error", errorMessage);
        return ResponseEntity.status(status).body(response);
    }
}
