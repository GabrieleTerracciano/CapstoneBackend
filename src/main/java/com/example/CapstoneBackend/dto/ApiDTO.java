package com.example.CapstoneBackend.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ApiDTO {
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

    private Map<String, Integer> addedByStatus;

    private List<String> ratingsTitles;
    private List<Integer> ratingsCounts;
    private List<Double> ratingsPercents;

    private String platformName;
    private String platformSlug;
    private String platformRequirementsMinimum;
    private String platformRequirementsRecommended;

    private String parentPlatformName;

    private List<String> genreNames;

    private List<String> storeNames;

    private List<String> tagNames;

    private List<String> screenshotUrls;
}
