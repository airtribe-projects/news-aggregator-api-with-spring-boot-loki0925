package com.airtribe.NewsAggregatorAPI.controller;
import com.airtribe.NewsAggregatorAPI.model.dto.PreferenceRequest;
import com.airtribe.NewsAggregatorAPI.model.enums.NewsCategory;
import com.airtribe.NewsAggregatorAPI.model.response.ApiResponse;
import com.airtribe.NewsAggregatorAPI.service.PreferencesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/preferences")
public class PreferencesController {

    private final PreferencesService preferenceService;

    public PreferencesController(PreferencesService preferenceService) {
        this.preferenceService = preferenceService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Set<NewsCategory>>> getUserPreferences() {
        Set<NewsCategory> preferences = preferenceService.getUserPreferences();
        return ResponseEntity.ok(new ApiResponse<>(true, "User preferences retrieved successfully", preferences));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<Set<NewsCategory>>> updateUserPreferences(
            @Valid @RequestBody PreferenceRequest preferenceRequest) {
        Set<NewsCategory> updatedPreferences = preferenceService.updateUserPreferences(preferenceRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "User preferences updated successfully", updatedPreferences));
    }
}

