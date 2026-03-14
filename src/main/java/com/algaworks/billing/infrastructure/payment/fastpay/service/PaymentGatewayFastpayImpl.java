package com.algaworks.billing.infrastructure.payment.fastpay.service;

import com.algaworks.billing.domain.model.invoice.payment.entity.Payment;
import com.algaworks.billing.domain.model.invoice.payment.request.PaymentRequest;
import com.algaworks.billing.domain.model.invoice.payment.service.PaymentGatewayService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "algashop.integrations.payment.provider", havingValue = "FASTPAY")
public class PaymentGatewayFastpayImpl implements PaymentGatewayService {
    @Override
    public Payment capture(PaymentRequest request) {
        return null;
    }

    @Override
    public Payment findByCode(String gatewayCode) {
        return null;
    }
}
