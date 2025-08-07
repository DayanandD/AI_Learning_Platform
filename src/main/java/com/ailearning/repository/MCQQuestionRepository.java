package com.ailearning.repository;

import com.ailearning.entity.MCQQuestion;
import com.ailearning.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MCQQuestionRepository extends JpaRepository<MCQQuestion, Long> {
    
    List<MCQQuestion> findByTopicAndActiveTrue(Topic topic);
    
    List<MCQQuestion> findByTopicIdAndActiveTrue(Long topicId);
    
    @Query("SELECT q FROM MCQQuestion q WHERE q.topic = :topic AND q.difficulty = :difficulty AND q.active = true")
    List<MCQQuestion> findByTopicAndDifficultyAndActiveTrue(@Param("topic") Topic topic, @Param("difficulty") Topic.Difficulty difficulty);
    
    @Query("SELECT q FROM MCQQuestion q WHERE q.topic.id = :topicId AND q.active = true ORDER BY RANDOM() LIMIT :limit")
    List<MCQQuestion> findRandomQuestionsByTopicId(@Param("topicId") Long topicId, @Param("limit") int limit);
    
    long countByTopicAndActiveTrue(Topic topic);
}
