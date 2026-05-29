package com.shutterflow.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "photos",
        indexes = {
                @Index(name = "idx_photo_gallery",
                        columnList = "gallery_id"),
                @Index(name = "idx_photo_favorite",
                        columnList = "is_favorite"),
                @Index(name = "idx_photo_hidden",
                        columnList = "is_hidden")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gallery_id", nullable = false)
    private Gallery gallery;

    @Column(nullable = false)
    private String fileName;

    private String originalFileName;

    // AWS S3 URLs
    @Column(nullable = false)
    private String originalUrl;

    private String thumbnailUrl;
    private String webUrl;
    private String printUrl;
    private String watermarkedUrl;

    // File details
    private Long fileSizeBytes;
    private Integer width;
    private Integer height;
    private String mimeType;
    private String format; // JPG, PNG, RAW

    // EXIF data
    private String cameraModel;
    private String lensModel;
    private String aperture;
    private String shutterSpeed;
    private String iso;
    private String focalLength;
    private LocalDateTime takenAt;
    private Double latitude;
    private Double longitude;

    // Organization
    private String section; // Album section name
    private Integer sortOrder;

    // Status
    private Boolean isFavorite;
    private Boolean isHidden;
    private Boolean isDeliverable;

    // Client interaction
    private Integer favoriteCount;
    @Column(columnDefinition = "TEXT")
    private String clientComment;

    // Download tracking
    private Integer downloadCount;
    private LocalDateTime lastDownloadedAt;

    @CreationTimestamp
    private LocalDateTime uploadedAt;

    @PrePersist
    public void prePersist() {
        if (isFavorite == null) isFavorite = false;
        if (isHidden == null) isHidden = false;
        if (isDeliverable == null) isDeliverable = true;
        if (favoriteCount == null) favoriteCount = 0;
        if (downloadCount == null) downloadCount = 0;
        if (sortOrder == null) sortOrder = 0;
    }
}