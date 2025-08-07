package com.ailearning.repository;

import com.ailearning.entity.UserProgress;
import com.ailearning.entity.User;
import com.ailearning.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {
    
    Optional<UserProgress> findByUserAndTopic(User user, Topic topic);
    
    Optional<UserProgress> findByUserIdAndTopicId(Long userId, Long topicId);
    
    List<UserProgress> findByUser(User user);
    
    List<UserProgress> findByUserId(Long userId);
    
    @Query("SELECT up FROM UserProgress up WHERE up.user.id = :userId AND up.topic.language.id = :languageId")
    List<UserProgress> findByUserIdAndLanguageId(@Param("userId") Long userId, @Param("languageId") Long languageId);
    
    @Query("SELECT up FROM UserProgress up WHERE up.user = :user AND up.status = :status")
    List<UserProgress> findByUserAndStatus(@Param("user") User user, @Param("status") UserProgress.ProgressStatus status);
    
    @Query("SELECT COUNT(up) FROM UserProgress up WHERE up.user = :user AND up.status = 'COMPLETED'")
    long countCompletedTopicsByUser(@Param("user") User user);
    
    @Query("SELECT AVG(up.progressPercentage) FROM UserProgress up WHERE up.user = :user")
    Double getAverageProgressByUser(@Param("user") User user);
}
