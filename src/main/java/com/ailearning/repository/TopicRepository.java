package com.ailearning.repository;

import com.ailearning.entity.Topic;
import com.ailearning.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    
    List<Topic> findByLanguageAndActiveTrue(Language language);
    
    List<Topic> findByLanguageIdAndActiveTrue(Long languageId);
    
    @Query("SELECT t FROM Topic t WHERE t.language.id = :languageId AND t.active = true ORDER BY t.sortOrder ASC, t.title ASC")
    List<Topic> findActiveTopicsByLanguageIdOrderedBySortOrder(@Param("languageId") Long languageId);
    
    @Query("SELECT t FROM Topic t WHERE t.language = :language AND t.difficulty = :difficulty AND t.active = true")
    List<Topic> findByLanguageAndDifficultyAndActiveTrue(@Param("language") Language language, @Param("difficulty") Topic.Difficulty difficulty);
    
    long countByLanguageAndActiveTrue(Language language);
}
