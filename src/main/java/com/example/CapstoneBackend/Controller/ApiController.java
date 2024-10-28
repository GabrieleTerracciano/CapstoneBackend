package com.example.CapstoneBackend.Controller;

import com.example.CapstoneBackend.Entity.Api;
import com.example.CapstoneBackend.Service.ApiService;
import com.example.CapstoneBackend.Service.VideogameService;
import com.example.CapstoneBackend.dto.ApiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/videogames")
public class ApiController {

    private final ApiService apiService;

    @Autowired
    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @Autowired
    private VideogameService videogameService;
    @GetMapping
    public List<Map<String, Object>> getAllApis() {
        return videogameService.getVideogames();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getApiById(@PathVariable Long id) {
        Map<String, Object> videogame = videogameService.getVideogameById(id);

        if (videogame == null || videogame.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(videogame);
    }

    @PostMapping
    public Api createApi(@RequestBody Api api) {
        return apiService.createApi(api);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Api> updateApi(@PathVariable Long id, @RequestBody ApiDTO apiDetails) {
        try {
            Api updatedApi = apiService.updateApi(id, apiDetails);
            return ResponseEntity.ok(updatedApi);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApi(@PathVariable Long id) {
        apiService.deleteApi(id);
        return ResponseEntity.noContent().build();
    }
}
