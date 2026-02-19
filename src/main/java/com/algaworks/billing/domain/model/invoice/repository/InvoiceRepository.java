package com.algaworks.billing.domain.model.invoice.repository;

import com.algaworks.billing.domain.model.invoice.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
    boolean existsByOrderId(String orderId);

    Optional<Invoice> findByOrderId(String orderId);
}
