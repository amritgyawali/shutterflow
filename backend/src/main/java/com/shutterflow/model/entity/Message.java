package com.shutterflow.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages",
        indexes = {
                @Index(name = "idx_message_booking",
                        columnList = "booking_id"),
                @Index(name = "idx_message_studio",
                        columnList = "studio_id"),
                @Index(name = "idx_message_read",
                        columnList = "is_read")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id", nullable = false)
    private Studio studio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    // Sender info (can be User or Client)
    private Long senderId;
    private String senderType; // PHOTOGRAPHER, CLIENT, STUDIO
    private String senderName;

    @Column(columnDefinition = "TEXT",
            nullable = false)
    private String message;

    // Attachments
    private String attachmentUrl;
    private String attachmentName;
    private String attachmentType;

    // Read status
    private Boolean isRead;
    private LocalDateTime readAt;

    // Email fallback
    private Boolean emailSent;
    private LocalDateTime emailSentAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (isRead == null) isRead = false;
        if (emailSent == null) emailSent = false;
    }
}