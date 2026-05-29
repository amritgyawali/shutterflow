package com.shutterflow.model.entity;

import com.shutterflow.model.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments",
        indexes = {
                @Index(name = "idx_payment_invoice",
                        columnList = "invoice_id"),
                @Index(name = "idx_payment_studio",
                        columnList = "studio_id"),
                @Index(name = "idx_payment_stripe",
                        columnList = "stripe_payment_id")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studio_id", nullable = false)
    private Studio studio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;

    // Stripe details
    private String stripePaymentId;
    private String stripeChargeId;
    private String stripeReceiptUrl;
    private String stripeCustomerId;

    // Status
    private String status; // PENDING, COMPLETED, FAILED, REFUNDED

    // Refund details
    private Boolean isRefunded;
    private Double refundAmount;
    private String refundReason;
    private LocalDateTime refundedAt;
    private String stripeRefundId;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private LocalDateTime paidAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (currency == null) currency = "AUD";
        if (status == null) status = "PENDING";
        if (isRefunded == null) isRefunded = false;
        if (paidAt == null) paidAt = LocalDateTime.now();
    }
}