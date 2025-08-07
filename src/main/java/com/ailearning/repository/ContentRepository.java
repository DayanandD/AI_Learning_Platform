package com.ailearning.repository;

import com.ailearning.entity.Content;
import com.ailearning.entity.Topic;
import com.ailearning.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
    
    List<Content> findByTopicAndActiveTrue(Topic topic);
    
    List<Content> findByTopicIdAndActiveTrue(Long topicId);
    
    @Query("SELECT c FROM Content c WHERE c.topic.id = :topicId AND c.active = true ORDER BY c.sortOrder ASC, c.title ASC")
    List<Content> findActiveContentsByTopicIdOrderedBySortOrder(@Param("topicId") Long topicId);
    
    @Query("SELECT c FROM Content c WHERE c.topic = :topic AND c.contentType = :contentType AND c.active = true")
    List<Content> findByTopicAndContentTypeAndActiveTrue(@Param("topic") Topic topic, @Param("contentType") Content.ContentType contentType);
    
    @Query("SELECT c FROM Content c WHERE c.topic = :topic AND c.targetLevel = :targetLevel AND c.active = true ORDER BY c.sortOrder ASC")
    List<Content> findByTopicAndTargetLevelAndActiveTrue(@Param("topic") Topic topic, @Param("targetLevel") User.LearningLevel targetLevel);
    
    long countByTopicAndActiveTrue(Topic topic);
}
