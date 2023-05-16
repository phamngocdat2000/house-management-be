package com.student.searchroom.repository;

import com.student.searchroom.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Rating findByCreatedByAndUsername(String createdBy, String username);

    List<Rating> findAllByUsername(String username);
    List<Rating> findAllByUsernameOrderByCreatedAt(String username);
}
