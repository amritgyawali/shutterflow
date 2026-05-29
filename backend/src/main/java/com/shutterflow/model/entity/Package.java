package com.shutterflow.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "packages",
        indexes = {
                @Index(name = "idx_package_studio",
                        columnList = "studio_id"),
                @Index(name = "idx_package_photographer",
                        columnList = "photographer_id")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id", nullable = false)
    private Studio studio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photographer_id")
    private User photographer;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String eventType; // WEDDING, PORTRAIT, etc

    @Column(nullable = false)
    private Double basePrice;

    private Double pricePerHour; // for extra hours
    private Integer baseHours;
    private Integer maxHours;

    // Deliverables
    @Column(columnDefinition = "TEXT")
    private String deliverables; // JSON array

    private Integer editedPhotosMin;
    private Integer editedPhotosMax;
    private Integer turnaroundDays;
    private Boolean includesAlbum;
    private Boolean includesPrints;
    private Boolean includesUSB;
    private Boolean includesOnlineGallery;
    private Boolean includesVideoHighlight;

    // Visibility
    private Boolean isPublic;
    private Boolean isFeatured;
    private Boolean isActive;

    // Pricing display
    private String priceDisplay; // "Starting from", "From"
    private String currency;

    // Sorting
    private Integer sortOrder;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (isPublic == null) isPublic = true;
        if (isFeatured == null) isFeatured = false;
        if (isActive == null) isActive = true;
        if (currency == null) currency = "AUD";
        if (includesOnlineGallery == null)
            includesOnlineGallery = true;
        if (priceDisplay == null)
            priceDisplay = "Starting from";
    }
}