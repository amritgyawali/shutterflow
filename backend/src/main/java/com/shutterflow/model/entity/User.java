package com.shutterflow.model.entity;

import com.shutterflow.model.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users",
    indexes = {
        @Index(name = "idx_users_email", columnList = "email", unique = true),
        @Index(name = "idx_users_studio", columnList = "studio_id"),
        @Index(name = "idx_users_role", columnList = "role"),
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id")
    private Studio studio;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phone;
    private String profilePhotoUrl;
    private String bio;
    private String websiteUrl;
    private String instagramUrl;

    // Location
    private String city;
    private String state;
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    // Portfolio
    private String portfolioUrl;
    private String specialties; // JSON array as string

    // Availability
    private String availableHours; // JSON config
    private Integer travelRadius;
    private Boolean availableForTravel;

    // Commission (for photographers in a studio)
    private Double commissionRate;

    // Security
    private Boolean isActive;
    private Boolean isEmailVerified;
    private String emailVerificationToken;
    private String passwordResetToken;
    private LocalDateTime passwordResetExpiresAt;

    // Session tracking
    private LocalDateTime lastLoginAt;
    private String lastLoginIp;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Relationships
    @OneToMany(mappedBy = "photographer",
            fetch = FetchType.LAZY)
    private List<Booking> bookings;

    @OneToMany(mappedBy = "photographer",
            fetch = FetchType.LAZY)
    private List<Review> reviews;

    @PrePersist
    public void prePersist() {
        if (isActive == null) isActive = true;
        if (isEmailVerified == null) isEmailVerified = false;
        if (availableForTravel == null) availableForTravel = true;
        if (country == null) country = "Australia";
    }
}
