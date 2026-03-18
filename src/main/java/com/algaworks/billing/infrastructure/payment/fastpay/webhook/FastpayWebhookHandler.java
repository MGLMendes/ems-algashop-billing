package com.algaworks.billing.infrastructure.payment.fastpay.webhook;

import com.algaworks.billing.application.invoice.service.InvoiceManagementApplicationService;
import com.algaworks.billing.infrastructure.payment.fastpay.enums.FastpayPaymentStatus;
import com.algaworks.billing.infrastructure.payment.fastpay.util.FastpayEnumConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class FastpayWebhookHandler {

    private final InvoiceManagementApplicationService invoiceApplicationService;

    public void process(FastpayWebhookEvent event) {
        log.info("Processing webhook event: {}", event);
        invoiceApplicationService.updatePaymentStatus(
                UUID.fromString(event.getReferenceCode()),
                        FastpayEnumConverter.convert(FastpayPaymentStatus.valueOf(event.getStatus())));
    }
}
