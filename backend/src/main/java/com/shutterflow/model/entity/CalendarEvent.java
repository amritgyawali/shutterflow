package com.shutterflow.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "calendar_events",
        indexes = {
                @Index(name = "idx_cal_studio",
                        columnList = "studio_id"),
                @Index(name = "idx_cal_photographer",
                        columnList = "photographer_id"),
                @Index(name = "idx_cal_start",
                        columnList = "start_date_time")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id", nullable = false)
    private Studio studio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photographer_id",
            nullable = false)
    private User photographer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    private Boolean isAllDay;

    private String type;
    // BOOKING, BLOCK, PERSONAL, TRAVEL

    private String color;
    private String location;

    // Google Calendar sync
    private String googleCalendarId;
    private String googleEventId;
    private LocalDateTime lastSyncedAt;

    private Boolean isRecurring;
    private String recurrenceRule;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (isAllDay == null) isAllDay = false;
        if (isRecurring == null) isRecurring = false;
        if (color == null) color = "#e94560";
    }
}