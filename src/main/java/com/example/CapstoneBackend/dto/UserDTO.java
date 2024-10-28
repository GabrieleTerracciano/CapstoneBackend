package com.example.CapstoneBackend.dto;

import com.example.CapstoneBackend.Enum.Role;
import lombok.Data;

@Data
public class UserDTO {
    private String name;
    private String surname;
    private String email;
    private Role role;
}
