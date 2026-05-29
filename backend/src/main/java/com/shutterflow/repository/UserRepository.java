package com.shutterflow.repository;

import com.shutterflow.model.entity.User;
import com.shutterflow.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository
        extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    List<User> findByStudioIdAndIsActiveTrue(
            Long studioId);

    List<User> findByStudioIdAndRole(
            Long studioId, UserRole role);

    Optional<User> findByEmailVerificationToken(
            String token);

    Optional<User> findByPasswordResetToken(
            String token);

    @Query("SELECT u FROM User u " +
            "WHERE u.studio.id = :studioId " +
            "AND u.role IN ('PHOTOGRAPHER','SECOND_SHOOTER') " +
            "AND u.isActive = true " +
            "ORDER BY u.fullName ASC")
    List<User> findPhotographersByStudio(Long studioId);
}