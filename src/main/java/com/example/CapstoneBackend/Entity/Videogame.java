package com.example.CapstoneBackend.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "videogames")
@Entity
public class Videogame {
    @Id
    @GeneratedValue
    private long id;
    private String gameKey;
    @ManyToOne
    private Order order;


}
