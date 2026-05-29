package com.shutterflow.model.entity;

import com.shutterflow.model.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "bookings",
        indexes = {
                @Index(name = "idx_booking_studio",
                        columnList = "studio_id"),
                @Index(name = "idx_booking_photographer",
                        columnList = "photographer_id"),
                @Index(name = "idx_booking_client",
                        columnList = "client_id"),
                @Index(name = "idx_booking_status",
                        columnList = "status"),
                @Index(name = "idx_booking_event_date",
                        columnList = "event_date")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Core relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id", nullable = false)
    private Studio studio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photographer_id",
            nullable = false)
    private User photographer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_shooter_id")
    private User secondShooter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id")
    private Package packageInfo;

    // Event details
    @Column(nullable = false)
    private String eventType;

    private String eventTitle;

    @Column(nullable = false)
    private LocalDate eventDate;

    @Column(nullable = false)
    private LocalTime eventTime;

    private LocalTime eventEndTime;

    @Column(nullable = false)
    private String eventLocation;

    private String eventAddress;
    private String eventSuburb;
    private String eventCity;
    private String eventState;
    private Double eventLatitude;
    private Double eventLongitude;

    // Duration and pricing
    @Column(nullable = false)
    private Integer durationHours;

    private Integer overtimeHours;
    private Double travelFee;
    private Double overtimeFee;

    @Column(nullable = false)
    private Double totalAmount;

    @Column(nullable = false)
    private Double depositAmount;

    private Boolean depositPaid;
    private LocalDateTime depositPaidAt;

    private Boolean balancePaid;
    private LocalDateTime balancePaidAt;

    private String currency;
    private Double taxRate;
    private Double taxAmount;

    // Status tracking
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    private LocalDateTime statusUpdatedAt;
    private String statusUpdatedBy;
    private String cancellationReason;

    // Notes
    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(columnDefinition = "TEXT")
    private String internalNotes;

    @Column(columnDefinition = "TEXT")
    private String clientInstructions;

    // Source tracking
    private String leadSource;
    private String referredBy;

    // Contract and questionnaire
    private Boolean contractSigned;
    private LocalDateTime contractSignedAt;
    private Boolean questionnaireCompleted;
    private LocalDateTime questionnaireCompletedAt;

    // Gallery
    private Boolean galleryDelivered;
    private LocalDateTime galleryDeliveredAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Relationships
    @OneToOne(mappedBy = "booking",
            fetch = FetchType.LAZY)
    private Invoice invoice;

    @OneToOne(mappedBy = "booking",
            fetch = FetchType.LAZY)
    private Gallery gallery;

    @OneToOne(mappedBy = "booking",
            fetch = FetchType.LAZY)
    private Contract contract;

    @OneToMany(mappedBy = "booking",
            fetch = FetchType.LAZY)
    private List<Message> messages;

    @PrePersist
    public void prePersist() {
        if (status == null) status = BookingStatus.PENDING;
        if (depositPaid == null) depositPaid = false;
        if (balancePaid == null) balancePaid = false;
        if (contractSigned == null) contractSigned = false;
        if (questionnaireCompleted == null)
            questionnaireCompleted = false;
        if (galleryDelivered == null)
            galleryDelivered = false;
        if (currency == null) currency = "AUD";
        if (taxRate == null) taxRate = 10.0;
    }
}