package com.algaworks.billing.domain.model.invoice;

import com.algaworks.billing.domain.model.invoice.enums.InvoiceStatus;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Invoice {

    @EqualsAndHashCode.Include
    private UUID id;
    private String orderId;
    private UUID customerId;

    private OffsetDateTime issuedAt;
    private OffsetDateTime paidAt;
    private OffsetDateTime canceledAt;
    private OffsetDateTime expiredAt;

    private BigDecimal totalAmount;
    private InvoiceStatus status;

    private PaymentSettings paymentSettings;

    private Set<LineItem> items = new HashSet<>();

    private Payer payer;

    private String cancelReason;
}
