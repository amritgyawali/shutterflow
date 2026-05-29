package com.shutterflow.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications",
        indexes = {
                @Index(name = "idx_notif_user",
                        columnList = "user_id"),
                @Index(name = "idx_notif_read",
                        columnList = "is_read")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String userType; // USER, CLIENT

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT",
            nullable = false)
    private String message;

    private String type;
    // BOOKING, PAYMENT, GALLERY,
    // MESSAGE, CONTRACT, REVIEW

    private String actionUrl;
    private String iconType;

    private Boolean isRead;
    private LocalDateTime readAt;

    // Push notification
    private Boolean pushSent;
    private Boolean emailSent;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (isRead == null) isRead = false;
        if (pushSent == null) pushSent = false;
        if (emailSent == null) emailSent = false;
    }
}