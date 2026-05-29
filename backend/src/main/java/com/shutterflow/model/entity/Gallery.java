package com.shutterflow.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "galleries",
        indexes = {
                @Index(name = "idx_gallery_booking",
                        columnList = "booking_id"),
                @Index(name = "idx_gallery_studio",
                        columnList = "studio_id"),
                @Index(name = "idx_gallery_token",
                        columnList = "access_token")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id", nullable = false)
    private Studio studio;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String coverPhotoUrl;
    private String accessToken; // unique URL token

    // Access control
    private Boolean isPublic;
    private String accessPin; // 4-6 digit PIN
    private LocalDateTime expiresAt;

    // Download settings
    private Boolean downloadEnabled;
    private String downloadSizes; // JSON: ["web","print","original"]
    private Integer downloadLimit;
    private LocalDateTime downloadExpiresAt;

    // Watermark
    private Boolean watermarkEnabled;
    private String watermarkText;
    private String watermarkLogoUrl;
    private Double watermarkOpacity;
    private String watermarkPosition;

    // Statistics
    private Integer totalPhotos;
    private Integer totalVideos;
    private Long totalSizeBytes;
    private Integer viewCount;
    private Integer downloadCount;
    private LocalDateTime lastViewedAt;

    // Status
    private String status; // PROCESSING, READY, DELIVERED

    private Boolean isDelivered;
    private LocalDateTime deliveredAt;

    // Client interaction
    private Boolean allowComments;
    private Boolean allowFavorites;
    private Boolean allowSharing;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Relationships
    @OneToMany(mappedBy = "gallery",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Photo> photos;

    @PrePersist
    public void prePersist() {
        if (isPublic == null) isPublic = false;
        if (downloadEnabled == null) downloadEnabled = true;
        if (watermarkEnabled == null) watermarkEnabled = false;
        if (watermarkOpacity == null) watermarkOpacity = 0.5;
        if (watermarkPosition == null) watermarkPosition = "BOTTOM_RIGHT";
        if (totalPhotos == null) totalPhotos = 0;
        if (totalVideos == null) totalVideos = 0;
        if (totalSizeBytes == null) totalSizeBytes = 0L;
        if (viewCount == null) viewCount = 0;
        if (downloadCount == null) downloadCount = 0;
        if (isDelivered == null) isDelivered = false;
        if (status == null) status = "PROCESSING";
        if (allowComments == null) allowComments = true;
        if (allowFavorites == null) allowFavorites = true;
        if (allowSharing == null) allowSharing = true;
    }
}