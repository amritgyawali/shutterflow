package com.shutterflow.model.entity;

import com.shutterflow.model.enums.InvoiceStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "invoices",
        indexes = {
                @Index(name = "idx_invoice_studio",
                        columnList = "studio_id"),
                @Index(name = "idx_invoice_booking",
                        columnList = "booking_id"),
                @Index(name = "idx_invoice_client",
                        columnList = "client_id"),
                @Index(name = "idx_invoice_status",
                        columnList = "status"),
                @Index(name = "idx_invoice_number",
                        columnList = "invoice_number",
                        unique = true)
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id", nullable = false)
    private Studio studio;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(nullable = false, unique = true)
    private String invoiceNumber;

    private String invoiceType; // DEPOSIT, BALANCE, FULL

    // Line items stored as JSON
    @Column(columnDefinition = "TEXT")
    private String lineItems;

    // Amounts
    @Column(nullable = false)
    private Double subtotal;

    private Double discountPercent;
    private Double discountAmount;
    private Double discountLabel;

    @Column(nullable = false)
    private Double taxPercent;

    @Column(nullable = false)
    private Double taxAmount;

    private String taxLabel;

    @Column(nullable = false)
    private Double totalAmount;

    @Column(nullable = false)
    private Double amountPaid;

    @Column(nullable = false)
    private Double balanceDue;

    @Column(nullable = false)
    private String currency;

    // Dates
    private LocalDate issueDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    private LocalDateTime sentAt;
    private LocalDateTime paidAt;
    private LocalDateTime viewedAt;

    // Status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvoiceStatus status;

    // Notes
    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(columnDefinition = "TEXT")
    private String paymentTerms;

    @Column(columnDefinition = "TEXT")
    private String footer;

    // Stripe
    private String stripePaymentIntentId;
    private String stripePaymentUrl;

    // Reminder tracking
    private Integer remindersSent;
    private LocalDateTime lastReminderAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Relationships
    @OneToMany(mappedBy = "invoice",
            fetch = FetchType.LAZY)
    private List<Payment> payments;

    @PrePersist
    public void prePersist() {
        if (status == null) status = InvoiceStatus.DRAFT;
        if (amountPaid == null) amountPaid = 0.0;
        if (currency == null) currency = "AUD";
        if (taxPercent == null) taxPercent = 10.0;
        if (taxLabel == null) taxLabel = "GST";
        if (remindersSent == null) remindersSent = 0;
        if (issueDate == null) issueDate = LocalDate.now();
        if (balanceDue == null) {
            balanceDue = totalAmount != null
                    ? totalAmount - amountPaid
                    : 0.0;
        }
    }
}