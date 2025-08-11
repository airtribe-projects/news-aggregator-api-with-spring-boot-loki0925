package com.airtribe.NewsAggregatorAPI.service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.airtribe.NewsAggregatorAPI.entity.User;
import com.airtribe.NewsAggregatorAPI.model.dto.PreferenceRequest;
import com.airtribe.NewsAggregatorAPI.repository.NewsPreferenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.airtribe.NewsAggregatorAPI.model.enums.NewsCategory;
import com.airtribe.NewsAggregatorAPI.entity.NewsPreference;


@Service
public class PreferencesService {

    private final NewsPreferenceRepository preferenceRepository;
    private final AuthenticationService authService;

    public PreferencesService(NewsPreferenceRepository preferenceRepository, AuthenticationService authService) {
        this.preferenceRepository = preferenceRepository;
        this.authService = authService;
    }

    public Set<NewsCategory> getUserPreferences() {
        User currentUser = authService.getCurrentUser();
        return preferenceRepository.findByUser(currentUser).stream()
                .map(NewsPreference::getCategory)
                .collect(Collectors.toSet());
    }

    @Transactional
    public Set<NewsCategory> updateUserPreferences(PreferenceRequest preferenceRequest) {
        User currentUser = authService.getCurrentUser();

        // Clear existing preferences
        Set<NewsPreference> existingPreferences = preferenceRepository.findByUser(currentUser);
        preferenceRepository.deleteAll(existingPreferences);

        // Save new preferences
        Set<NewsPreference> newPreferences = new HashSet<>();
        for (NewsCategory category : preferenceRequest.getCategories()) {
            NewsPreference preference = new NewsPreference();
            preference.setCategory(category);
            preference.setUser(currentUser);
            newPreferences.add(preference);
        }

        preferenceRepository.saveAll(newPreferences);

        return preferenceRequest.getCategories();
    }
}