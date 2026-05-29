package com.shutterflow.repository;

import com.shutterflow.model.entity.Invoice;
import com.shutterflow.model.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository
        extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findByInvoiceNumber(
            String invoiceNumber);

    List<Invoice> findByStudioIdOrderByCreatedAtDesc(
            Long studioId);

    List<Invoice> findByClientIdOrderByCreatedAtDesc(
            Long clientId);

    List<Invoice> findByStudioIdAndStatus(
            Long studioId, InvoiceStatus status);

    @Query("SELECT COALESCE(MAX(" +
            "CAST(SUBSTRING(i.invoiceNumber, " +
            "LENGTH(:prefix) + 2) AS int)), " +
            ":startNumber - 1) " +
            "FROM Invoice i " +
            "WHERE i.studio.id = :studioId " +
            "AND i.invoiceNumber LIKE CONCAT(:prefix, '-%')")
    Integer getLastInvoiceNumber(
            Long studioId,
            String prefix,
            Integer startNumber);

    @Query("SELECT SUM(i.totalAmount) " +
            "FROM Invoice i " +
            "WHERE i.studio.id = :studioId " +
            "AND i.status = 'PAID'")
    Double getTotalPaidAmount(Long studioId);

    @Query("SELECT SUM(i.balanceDue) " +
            "FROM Invoice i " +
            "WHERE i.studio.id = :studioId " +
            "AND i.status IN ('SENT','PARTIAL','OVERDUE')")
    Double getTotalOutstanding(Long studioId);
}