package com.example.CapstoneBackend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Table(name = "orders")
@Entity
public class Order {
    @Id
    @GeneratedValue
    private long id;
    @OneToMany(mappedBy = "order")
    private List<Videogame> videogameList;
    private double totalPrice;
    @ManyToOne
    @JsonIgnore
    private User user;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", user=" + user +
                '}';
    }
}
