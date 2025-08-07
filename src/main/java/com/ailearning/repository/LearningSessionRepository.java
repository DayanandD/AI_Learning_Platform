package com.ailearning.repository;

import com.ailearning.entity.LearningSession;
import com.ailearning.entity.User;
import com.ailearning.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LearningSessionRepository extends JpaRepository<LearningSession, Long> {
    
    List<LearningSession> findByUser(User user);
    
    List<LearningSession> findByUserId(Long userId);
    
    List<LearningSession> findByUserAndTopic(User user, Topic topic);
    
    @Query("SELECT ls FROM LearningSession ls WHERE ls.user.id = :userId ORDER BY ls.startTime DESC")
    List<LearningSession> findByUserIdOrderByStartTimeDesc(@Param("userId") Long userId);
    
    @Query("SELECT ls FROM LearningSession ls WHERE ls.user = :user AND ls.startTime >= :startDate AND ls.startTime <= :endDate")
    List<LearningSession> findByUserAndDateRange(@Param("user") User user, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT SUM(ls.durationMinutes) FROM LearningSession ls WHERE ls.user = :user")
    Long getTotalLearningTimeByUser(@Param("user") User user);
    
    @Query("SELECT SUM(ls.durationMinutes) FROM LearningSession ls WHERE ls.user = :user AND ls.topic = :topic")
    Long getTotalLearningTimeByUserAndTopic(@Param("user") User user, @Param("topic") Topic topic);
}
