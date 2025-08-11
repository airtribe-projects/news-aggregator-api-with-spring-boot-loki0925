package com.airtribe.NewsAggregatorAPI.repository;

import java.util.Set;
import com.airtribe.NewsAggregatorAPI.entity.NewsPreference;
import com.airtribe.NewsAggregatorAPI.entity.User;
import com.airtribe.NewsAggregatorAPI.model.enums.NewsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsPreferenceRepository extends JpaRepository<NewsPreference, Long> {

    Set<NewsPreference> findByUser(User user);

    void deleteByUserAndCategory(User user, NewsCategory category);

    boolean existsByUserAndCategory(User user, NewsCategory category);
}
