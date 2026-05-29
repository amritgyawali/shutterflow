package com.shutterflow.repository;

import com.shutterflow.model.entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GalleryRepository
        extends JpaRepository<Gallery, Long> {

    Optional<Gallery> findByAccessToken(
            String accessToken);

    Optional<Gallery> findByBookingId(Long bookingId);

    boolean existsByBookingId(Long bookingId);
}