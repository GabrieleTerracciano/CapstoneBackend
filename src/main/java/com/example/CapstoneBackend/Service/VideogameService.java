package com.example.CapstoneBackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class VideogameService {
    @Autowired
    private RestTemplate restTemplate;

    private String baseUrl = "https://api.rawg.io/api/games";
    private String apiKey = "ef3658651dcf4bc9be444420dd0a64fb";

    public List<Map<String, Object>> getVideogames() {
        String url = baseUrl + "?key=" + apiKey;

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        assert response != null;
        List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");

        return results;
    }

    public Map<String, Object> getVideogameById(Long id) {
        String url = baseUrl + "/" + id + "?key=" + apiKey;

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        return response;
    }
}
