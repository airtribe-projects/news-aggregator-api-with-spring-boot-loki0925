package com.airtribe.NewsAggregatorAPI.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    @Column(nullable = false, length = 128)
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<NewsPreference> preferences = new HashSet<>();

    public User(Long userId, String email, String password, Set<NewsPreference> preferences) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.preferences = preferences;
    }

    public User() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static User createUser() {
        return new User();
    }

    public Set<NewsPreference> getPreferences() {
        return preferences;
    }

    public void setPreferences(Set<NewsPreference> preferences) {
        this.preferences = preferences;
    }
}
