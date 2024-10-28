package com.example.CapstoneBackend.Entity;

import com.example.CapstoneBackend.Enum.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Table(name = "users")
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String surname;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Order> orderList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null) {
            throw new IllegalStateException("Role is not set for this user");
        }
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return this.email; // Restituisce l'email come username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Puoi implementare logica aggiuntiva qui
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Puoi implementare logica aggiuntiva qui
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Puoi implementare logica aggiuntiva qui
    }

    @Override
    public boolean isEnabled() {
        return true; // Puoi implementare logica aggiuntiva qui
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
