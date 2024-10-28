package com.example.CapstoneBackend.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Entity
public class Api {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String slug;
    private String name;
    private String description;
    private String released;
    private boolean tba;
    private String backgroundImage;
    private double rating;
    private int ratingTop;
    private int ratingsCount;
    private int reviewsTextCount;
    private int added;
    private int metacritic;
    private int playtime;
    private int suggestionsCount;
    private String updated;
    private String saturatedColor;
    private String dominantColor;
    private Integer reviewsCount;

    private String esrbRatingName;
    private String esrbRatingSlug;

    @ElementCollection
    @CollectionTable(name = "added_by_status", joinColumns = @JoinColumn(name = "api_id"))
    @MapKeyColumn(name = "status")
    @Column(name = "count")
    private Map<String, Integer> addedByStatus;

    @ElementCollection
    private List<String> ratingsTitles;
    @ElementCollection
    private List<Integer> ratingsCounts;
    @ElementCollection
    private List<Double> ratingsPercents;

    private String platformName;
    private String platformSlug;
    private String platformRequirementsMinimum;
    private String platformRequirementsRecommended;

    private String parentPlatformName;

    @ElementCollection
    private List<String> genreNames;

    @ElementCollection
    private List<String> storeNames;

    @ElementCollection
    private List<String> tagNames;

    @ElementCollection
    private List<String> screenshotUrls;

}
