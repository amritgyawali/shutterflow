package com.shutterflow.repository;

import com.shutterflow.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository
        extends JpaRepository<Review, Long> {

    List<Review> findByPhotographerIdAndIsPublicTrue(
            Long photographerId);

    List<Review> findByStudioIdAndIsPublicTrue(
            Long studioId);

    @Query("SELECT AVG(r.rating) FROM Review r " +
            "WHERE r.photographer.id = :photographerId " +
            "AND r.isPublic = true")
    Double getAverageRating(Long photographerId);

    @Query("SELECT COUNT(r) FROM Review r " +
            "WHERE r.photographer.id = :photographerId " +
            "AND r.isApproved = false")
    Long countPendingReviews(Long photographerId);
}