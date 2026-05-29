package com.shutterflow.repository;

import com.shutterflow.model.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository
        extends JpaRepository<Client, Long> {

    Optional<Client> findByPortalToken(String token);

    List<Client> findByStudioIdAndIsActiveTrueOrderByFullNameAsc(
            Long studioId);

    Page<Client> findByStudioIdAndIsActiveTrue(
            Long studioId, Pageable pageable);

    @Query("SELECT c FROM Client c " +
            "WHERE c.studio.id = :studioId " +
            "AND (LOWER(c.fullName) LIKE LOWER(CONCAT('%',:q,'%')) " +
            "OR LOWER(c.email) LIKE LOWER(CONCAT('%',:q,'%')) " +
            "OR LOWER(c.phone) LIKE LOWER(CONCAT('%',:q,'%')))")
    Page<Client> searchClients(
            Long studioId, String q, Pageable pageable);

    boolean existsByEmailAndStudioId(
            String email, Long studioId);

    @Query("SELECT COUNT(c) FROM Client c " +
            "WHERE c.studio.id = :studioId " +
            "AND c.isActive = true")
    Long countActiveClients(Long studioId);
}