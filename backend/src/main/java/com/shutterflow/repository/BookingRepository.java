package com.shutterflow.repository;

import com.shutterflow.model.entity.Booking;
import com.shutterflow.model.enums.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository
        extends JpaRepository<Booking, Long> {

    List<Booking> findByStudioIdOrderByEventDateDesc(
            Long studioId);

    List<Booking> findByPhotographerIdOrderByEventDateDesc(
            Long photographerId);

    Page<Booking> findByStudioId(
            Long studioId, Pageable pageable);

    List<Booking> findByStudioIdAndStatus(
            Long studioId, BookingStatus status);

    List<Booking> findByStudioIdAndEventDateBetween(
            Long studioId,
            LocalDate startDate,
            LocalDate endDate);

    boolean existsByPhotographerIdAndEventDate(
            Long photographerId, LocalDate eventDate);

    @Query("SELECT COUNT(b) FROM Booking b " +
            "WHERE b.studio.id = :studioId " +
            "AND b.status NOT IN ('CANCELLED','ARCHIVED')")
    Long countActiveBookings(Long studioId);

    @Query("SELECT SUM(b.totalAmount) " +
            "FROM Booking b " +
            "WHERE b.studio.id = :studioId " +
            "AND b.status = 'COMPLETED'")
    Double getTotalRevenue(Long studioId);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.studio.id = :studioId " +
            "AND b.eventDate >= :today " +
            "AND b.status NOT IN ('CANCELLED','ARCHIVED') " +
            "ORDER BY b.eventDate ASC")
    List<Booking> findUpcomingBookings(
            Long studioId, LocalDate today);
}