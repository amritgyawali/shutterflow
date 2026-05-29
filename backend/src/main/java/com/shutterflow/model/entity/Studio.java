package com.shutterflow.model.entity;

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
@Table(name = "studios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Studio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String studioName;

    @Column(nullable = false, unique = true)
    private String studioEmail;

    private String studioPhone;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String logoUrl;
    private String coverPhotoUrl;
    private String websiteUrl;
    private String instagramUrl;
    private String facebookUrl;
    private String twitterUrl;

    @Column(nullable = false)
    private String address;

    private String suburb;
    private String city;
    private String state;
    private String country;
    private String postcode;

    private String abn;
    private String businessName;

    private String primaryColor;
    private String secondaryColor;
    private String customDomain;
    private String portalSlug; // client.shutterflow.com/[slug]

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudioPlan plan;

    private LocalDateTime planExpiresAt;
    private Boolean isTrialing;
    private LocalDateTime trialEndsAt;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private String timezone;

    private Double defaultTaxRate;
    private String taxLabel;
    private String invoicePrefix;
    private Integer invoiceStartNumber;

    private String bankName;
    private String bankAccountName;
    private String bankAccountNumber;
    private String bankBsb;

    @Column(nullable = false)
    private Boolean isActive;

    private Boolean isWhiteLabel;
    private Boolean removeBranding;

    @CreationTimestamp
    private LocalDateTime cratedAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> users;

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Client> clients;

    @OneToMany(mappedBy = "studio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Booking> bookings;

    @PrePersist
    public void prePersist() {
        if (isActive == null) isActive = true;
        if (isTrialing == null) isTrialing = true;
        if (currency == null) currency = "AUD";
        if (timezone == null) timezone = "Australia/Melbourne";
        if (defaultTaxRate == null) defaultTaxRate = 10.0;
        if (taxLabel == null) taxLabel = "GST";
        if (invoicePrefix == null) invoicePrefix = "INV";
        if (invoiceStartNumber == null) invoiceStartNumber = 1001;
        if (plan == null) plan = StudioPlan.STARTER;
        if (isWhiteLabel == null) isWhiteLabel = false;
        if (removeBranding == null) removeBranding = false;
        if (trialEndsAt == null) trialEndsAt = LocalDateTime.now().plusDays(14);
    }

    public enum StudioPlan {
        STARTER,
        PRO,
        STUDIO
    }
}
