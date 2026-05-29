package com.shutterflow.repository;

import com.shutterflow.model.entity.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudioRepository
        extends JpaRepository<Studio, Long> {

    Optional<Studio> findByStudioEmail(String email);
    Optional<Studio> findByPortalSlug(String slug);
    Optional<Studio> findByCustomDomain(String domain);
    boolean existsByStudioEmail(String email);
    boolean existsByPortalSlug(String slug);

    @Query("SELECT COUNT(u) FROM User u " +
            "WHERE u.studio.id = :studioId " +
            "AND u.isActive = true")
    Long countActiveUsers(Long studioId);
}