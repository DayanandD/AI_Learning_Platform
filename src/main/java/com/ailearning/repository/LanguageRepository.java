package com.ailearning.repository;

import com.ailearning.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
    
    Optional<Language> findByName(String name);
    
    List<Language> findByActiveTrue();
    
    @Query("SELECT l FROM Language l WHERE l.active = true ORDER BY l.sortOrder ASC, l.name ASC")
    List<Language> findActiveLanguagesOrderedBySortOrder();
    
    boolean existsByName(String name);
}
