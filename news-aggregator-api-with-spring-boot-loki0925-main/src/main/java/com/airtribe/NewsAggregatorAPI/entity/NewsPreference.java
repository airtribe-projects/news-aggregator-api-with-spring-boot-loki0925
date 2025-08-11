package com.airtribe.NewsAggregatorAPI.entity;

import com.airtribe.NewsAggregatorAPI.model.enums.NewsCategory;
import jakarta.persistence.*;


@Entity
@Table(name = "news_preferences")
public class NewsPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NewsCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public NewsPreference(Long id, NewsCategory category, User user) {
        this.id = id;
        this.category = category;
        this.user = user;
    }

    public NewsPreference() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NewsCategory getCategory() {
        return category;
    }

    public void setCategory(NewsCategory category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
