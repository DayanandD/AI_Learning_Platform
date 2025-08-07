package com.ailearning.repository;

import com.ailearning.entity.UserMCQAttempt;
import com.ailearning.entity.User;
import com.ailearning.entity.MCQQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMCQAttemptRepository extends JpaRepository<UserMCQAttempt, Long> {
    
    List<UserMCQAttempt> findByUser(User user);
    
    List<UserMCQAttempt> findByUserId(Long userId);
    
    List<UserMCQAttempt> findByUserAndQuestion(User user, MCQQuestion question);
    
    @Query("SELECT ua FROM UserMCQAttempt ua WHERE ua.user.id = :userId AND ua.question.topic.id = :topicId")
    List<UserMCQAttempt> findByUserIdAndTopicId(@Param("userId") Long userId, @Param("topicId") Long topicId);
    
    @Query("SELECT COUNT(ua) FROM UserMCQAttempt ua WHERE ua.user = :user AND ua.isCorrect = true")
    long countCorrectAnswersByUser(@Param("user") User user);
    
    @Query("SELECT SUM(ua.pointsEarned) FROM UserMCQAttempt ua WHERE ua.user = :user")
    Long getTotalPointsByUser(@Param("user") User user);
    
    @Query("SELECT AVG(CASE WHEN ua.isCorrect = true THEN 1.0 ELSE 0.0 END) FROM UserMCQAttempt ua WHERE ua.user = :user")
    Double getAccuracyByUser(@Param("user") User user);
}
