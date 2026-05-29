package com.shutterflow.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "contracts",
        indexes = {
                @Index(name = "idx_contract_booking",
                        columnList = "booking_id"),
                @Index(name = "idx_contract_studio",
                        columnList = "studio_id")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id", nullable = false)
    private Studio studio;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private ContractTemplate template;

    @Column(columnDefinition = "LONGTEXT",
            nullable = false)
    private String content;

    // Client signature
    @Column(columnDefinition = "LONGTEXT")
    private String clientSignatureData; // Base64 PNG

    private String clientSignedName;
    private LocalDateTime clientSignedAt;
    private String clientSignedIp;
    private String clientSignedUserAgent;

    // Photographer countersignature
    @Column(columnDefinition = "LONGTEXT")
    private String photographerSignatureData;

    private String photographerSignedName;
    private LocalDateTime photographerSignedAt;

    // Status
    private String status; // DRAFT,SENT,CLIENT_SIGNED,FULLY_SIGNED

    private LocalDateTime sentAt;
    private LocalDateTime viewedAt;

    // PDF
    private String signedPdfUrl;

    // Reminders
    private Integer remindersSent;
    private LocalDateTime lastReminderAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (status == null) status = "DRAFT";
        if (remindersSent == null) remindersSent = 0;
    }
}