package com.shutterflow.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews",
        indexes = {
                @Index(name = "idx_review_photographer",
                        columnList = "photographer_id"),
                @Index(name = "idx_review_studio",
                        columnList = "studio_id"),
                @Index(name = "idx_review_booking",
                        columnList = "booking_id")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id", nullable = false)
    private Studio studio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photographer_id",
            nullable = false)
    private User photographer;

    @Column(nullable = false)
    private Integer rating; // 1-5

    @Column(columnDefinition = "TEXT")
    private String reviewText;

    // Detailed ratings
    private Integer ratingProfessionalism;
    private Integer ratingQuality;
    private Integer ratingCommunication;
    private Integer ratingValue;

    // Photographer response
    @Column(columnDefinition = "TEXT")
    private String photographerResponse;

    private LocalDateTime respondedAt;

    // Moderation
    private Boolean isApproved;
    private Boolean isPublic;
    private Boolean isFeatured;

    private LocalDateTime approvedAt;

    // Google review
    private Boolean googleReviewRequested;
    private LocalDateTime googleReviewRequestedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (isApproved == null) isApproved = false;
        if (isPublic == null) isPublic = false;
        if (isFeatured == null) isFeatured = false;
        if (googleReviewRequested == null)
            googleReviewRequested = false;
    }
}