package com.algaworks.billing.infrastructure.payment.fastpay.webhook;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class FastpayWebhookEvent {
    @NotBlank
    private String paymentId;
    @NotBlank
    private String referenceCode;
    @NotBlank
    private String status;
    @NotBlank
    private String method;
    @NotNull
    private OffsetDateTime notifiedAt;
}
