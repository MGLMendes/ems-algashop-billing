package com.algaworks.billing.infrastructure.payment.fastpay.service;

import com.algaworks.billing.domain.model.creditcard.entity.CreditCard;
import com.algaworks.billing.domain.model.creditcard.entity.LimitedCreditCard;
import com.algaworks.billing.domain.model.creditcard.repository.CreditCardRepository;
import com.algaworks.billing.domain.model.invoice.InvoiceTestDataBuilder;
import com.algaworks.billing.domain.model.invoice.enums.PaymentMethod;
import com.algaworks.billing.domain.model.invoice.payment.entity.Payment;
import com.algaworks.billing.domain.model.invoice.payment.request.PaymentRequest;
import com.algaworks.billing.infrastructure.AbstractFastpayIT;
import com.algaworks.billing.infrastructure.creditcard.service.fastpay.config.FastpayCreditCardTokenizationAPIClientConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
@Import(FastpayCreditCardTokenizationAPIClientConfig.class)
@Transactional
class PaymentGatewayFastpayServiceImplIT extends AbstractFastpayIT {

    @Autowired
    private PaymentGatewayFastpayServiceImpl  paymentGatewayFastpayServiceImpl;

    @Autowired
    private CreditCardRepository creditCardRepository;

    private static final UUID validCustomerId = UUID.randomUUID();

    @Test
    void shouldProcessPaymentWithCreditCard() {
        LimitedCreditCard limitedCreditCard = registerCard();

        CreditCard creditCard = CreditCard.brandNew(
                validCustomerId,
                limitedCreditCard.getLastNumbers(),
                limitedCreditCard.getBrand(),
                limitedCreditCard.getExpirationMonth(),
                limitedCreditCard.getExpirationYear(),
                limitedCreditCard.getGatewayCode()
        );

        creditCardRepository.save(creditCard);
        UUID invoiceId = UUID.randomUUID();
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .method(PaymentMethod.CREDIT_CARD)
                .amount(new BigDecimal("1000.0"))
                .invoiceId(invoiceId)
                .creditCardId(creditCard.getId())
                .payer(InvoiceTestDataBuilder.aPayer())
                .build();

        Payment payment = paymentGatewayFastpayServiceImpl.capture(paymentRequest);

        Assertions.assertThat(payment.getInvoiceId()).isEqualTo(invoiceId);
    }
}