package com.example.CapstoneBackend;

import com.example.CapstoneBackend.Controller.ApiController;
import com.example.CapstoneBackend.Service.ApiService;
import com.example.CapstoneBackend.Service.VideogameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Runner implements CommandLineRunner {
    @Autowired
    private VideogameService videogameService;

    @Autowired
    private ApiService apiService;

    @Autowired
    private ApiController apiController;
    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(apiController.getAllApis());
    }
}