package com.shutterflow.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "clients",
        indexes = {
                @Index(name = "idx_client_email",
                        columnList = "email"),
                @Index(name = "idx_client_studio",
                        columnList = "studio_id"),
                @Index(name = "idx_client_photographer",
                        columnList = "photographer_id")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id", nullable = false)
    private Studio studio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photographer_id")
    private User photographer;

    // Primary contact
    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String email;

    private String phone;

    // Partner details (for weddings)
    private String partnerName;
    private String partnerEmail;
    private String partnerPhone;

    // Address
    private String address;
    private String suburb;
    private String city;
    private String state;
    private String country;
    private String postcode;

    // CRM
    private String tags; // comma separated: VIP,Corporate
    private String source; // Instagram,Google,Referral
    private String referredBy;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(columnDefinition = "TEXT")
    private String internalNotes;

    // Important dates
    private LocalDate birthday;
    private LocalDate anniversary;
    private LocalDate weddingDate;

    // Statistics (auto-calculated)
    private Integer totalBookings;
    private Double totalSpent;
    private LocalDateTime lastBookingAt;

    // Client Portal
    private String portalToken;
    private LocalDateTime portalTokenExpiresAt;
    private Boolean portalEnabled;
    private LocalDateTime lastPortalLoginAt;

    // Status
    private Boolean isActive;
    private Boolean isArchived;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Relationships
    @OneToMany(mappedBy = "client",
            fetch = FetchType.LAZY)
    private List<Booking> bookings;

    @OneToMany(mappedBy = "client",
            fetch = FetchType.LAZY)
    private List<Invoice> invoices;

    @PrePersist
    public void prePersist() {
        if (isActive == null) isActive = true;
        if (isArchived == null) isArchived = false;
        if (portalEnabled == null) portalEnabled = true;
        if (totalBookings == null) totalBookings = 0;
        if (totalSpent == null) totalSpent = 0.0;
        if (country == null) country = "Australia";
    }
}