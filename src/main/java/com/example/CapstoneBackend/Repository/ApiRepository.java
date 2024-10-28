package com.example.CapstoneBackend.Repository;

import com.example.CapstoneBackend.Entity.Api;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiRepository extends JpaRepository<Api, Long> {
}
