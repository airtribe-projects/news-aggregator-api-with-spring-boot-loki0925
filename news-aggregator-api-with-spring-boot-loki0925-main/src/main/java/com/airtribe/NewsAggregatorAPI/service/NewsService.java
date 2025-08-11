package com.airtribe.NewsAggregatorAPI.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.airtribe.NewsAggregatorAPI.model.response.NewsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.airtribe.NewsAggregatorAPI.model.enums.NewsCategory;
import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class NewsService {

    private final WebClient webClient;
    private final PreferencesService preferenceService;

    @Value("${news.api.key}")
    private String apiKey;
    private final String apiUrl = "https://gnews.io/api/v4";

    public NewsService(WebClient.Builder webClientBuilder, PreferencesService preferenceService) {
        this.webClient = webClientBuilder.baseUrl(apiUrl).build();
        this.preferenceService = preferenceService;
    }

    public List<NewsResponse> getNewsForUser() {
        Map<NewsCategory, List<NewsResponse>> categorizedNews = fetchCategorizedNews();
        return categorizedNews.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private Map<NewsCategory, List<NewsResponse>> fetchCategorizedNews() {
        Set<NewsCategory> preferences = preferenceService.getUserPreferences();

        if (preferences.isEmpty()) {
            System.out.println("No user preferences found.");
            return Collections.emptyMap();
        }
        // Fetch news articles for each category asynchronously
        Map<NewsCategory, CompletableFuture<List<NewsResponse>>> futureNewsMap = preferences.stream()
                .collect(Collectors.toMap(
                        category -> category,
                        category -> fetchNewsByCategory(category)
                                .collectList()
                                .doOnNext(newsList -> System.out.println("Fetched " + newsList.size() + " articles for category: " + category))
                                .toFuture()
                ));

        return futureNewsMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().join()
                ));
    }


    private Mono<NewsResponse> mapToNewsResponse(JsonNode article, NewsCategory category) {
        String publishedAt = article.path("publishedAt").asText();
        LocalDateTime dateTime;

        try {
            dateTime = LocalDateTime.parse(publishedAt, DateTimeFormatter.ISO_DATE_TIME);
        } catch (Exception e) {
            dateTime = LocalDateTime.now();
        }

        return Mono.just(new NewsResponse(
                article.path("title").asText(),
                article.path("description").asText(),
                article.path("content").asText(),
                article.path("source").path("name").asText(),
                article.path("url").asText(),
                dateTime,
                category.name()
        ));
    }

    private Flux<NewsResponse> fetchNewsByCategory(NewsCategory category) {
        String url = apiUrl + "/top-headlines?category=" + category.name().toLowerCase() + "&lang=en&apikey=" + apiKey;

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .flatMapMany(response -> {
                    JsonNode articles = response.path("articles");
                    return Flux.fromIterable(articles)
                            .flatMap(article -> mapToNewsResponse(article, category));
                });
    }
}
