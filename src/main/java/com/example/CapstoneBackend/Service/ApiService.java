package com.example.CapstoneBackend.Service;

import com.example.CapstoneBackend.Entity.Api;
import com.example.CapstoneBackend.Repository.ApiRepository;
import com.example.CapstoneBackend.dto.ApiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApiService {

    private final ApiRepository apiRepository;

    @Autowired
    public ApiService(ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
    }

    public List<Api> getAllApis() {
        return apiRepository.findAll();
    }

    public Optional<Api> getApiById(Long id) {
        return apiRepository.findById(id);
    }

    public Api createApi(Api api) {
        return apiRepository.save(api);
    }

    public Api updateApi(Long id, ApiDTO apiDetails) {
        return apiRepository.findById(id)
                .map(api -> {
                    api.setSlug(apiDetails.getSlug());
                    api.setName(apiDetails.getName());
                    api.setReleased(apiDetails.getReleased());
                    api.setTba(apiDetails.isTba());
                    api.setBackgroundImage(apiDetails.getBackgroundImage());
                    api.setRating(apiDetails.getRating());
                    api.setRatingTop(apiDetails.getRatingTop());
                    api.setRatingsCount(apiDetails.getRatingsCount());
                    api.setReviewsTextCount(apiDetails.getReviewsTextCount());
                    api.setAdded(apiDetails.getAdded());
                    api.setMetacritic(apiDetails.getMetacritic());
                    api.setPlaytime(apiDetails.getPlaytime());
                    api.setSuggestionsCount(apiDetails.getSuggestionsCount());
                    api.setUpdated(apiDetails.getUpdated());
                    api.setSaturatedColor(apiDetails.getSaturatedColor());
                    api.setDominantColor(apiDetails.getDominantColor());
                    api.setReviewsCount(apiDetails.getReviewsCount());
                    api.setEsrbRatingName(apiDetails.getEsrbRatingName());
                    api.setEsrbRatingSlug(apiDetails.getEsrbRatingSlug());
                    api.setAddedByStatus(apiDetails.getAddedByStatus());
                    api.setRatingsTitles(apiDetails.getRatingsTitles());
                    api.setRatingsCounts(apiDetails.getRatingsCounts());
                    api.setRatingsPercents(apiDetails.getRatingsPercents());
                    api.setPlatformName(apiDetails.getPlatformName());
                    api.setPlatformSlug(apiDetails.getPlatformSlug());
                    api.setPlatformRequirementsMinimum(apiDetails.getPlatformRequirementsMinimum());
                    api.setPlatformRequirementsRecommended(apiDetails.getPlatformRequirementsRecommended());
                    api.setParentPlatformName(apiDetails.getParentPlatformName());
                    api.setGenreNames(apiDetails.getGenreNames());
                    api.setStoreNames(apiDetails.getStoreNames());
                    api.setTagNames(apiDetails.getTagNames());
                    api.setScreenshotUrls(apiDetails.getScreenshotUrls());
                    return apiRepository.save(api);
                })
                .orElseThrow(() -> new RuntimeException("Api non trovata con id " + id));
    }

    public void deleteApi(Long id) {
        apiRepository.deleteById(id);
    }
}
