package com.example.CapstoneBackend.Model;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data

public class Error {
    private String message;
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String details;
}
