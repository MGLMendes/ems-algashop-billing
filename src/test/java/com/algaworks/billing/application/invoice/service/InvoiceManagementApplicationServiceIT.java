package com.algaworks.billing.application.invoice.service;

import com.algaworks.billing.application.invoice.input.GenerateInvoiceInput;
import com.algaworks.billing.application.invoice.input.PaymentSettingsInput;
import com.algaworks.billing.domain.model.creditcard.CreditCardTestDataBuilder;
import com.algaworks.billing.domain.model.creditcard.entity.CreditCard;
import com.algaworks.billing.domain.model.creditcard.repository.CreditCardRepository;
import com.algaworks.billing.domain.model.invoice.InvoiceTestDataBuilder;
import com.algaworks.billing.domain.model.invoice.entity.Invoice;
import com.algaworks.billing.domain.model.invoice.enums.InvoiceStatus;
import com.algaworks.billing.domain.model.invoice.enums.PaymentMethod;
import com.algaworks.billing.domain.model.invoice.event.InvoiceCanceledEvent;
import com.algaworks.billing.domain.model.invoice.event.InvoiceIssuedEvent;
import com.algaworks.billing.domain.model.invoice.event.InvoicePaidEvent;
import com.algaworks.billing.domain.model.invoice.payment.entity.Payment;
import com.algaworks.billing.domain.model.invoice.payment.enums.PaymentStatus;
import com.algaworks.billing.domain.model.invoice.payment.request.PaymentRequest;
import com.algaworks.billing.domain.model.invoice.payment.service.PaymentGatewayService;
import com.algaworks.billing.domain.model.invoice.repository.InvoiceRepository;
import com.algaworks.billing.domain.model.invoice.service.InvoiceService;
import com.algaworks.billing.infrastructure.listener.InvoiceEventListener;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Transactional
class InvoiceManagementApplicationServiceIT {


    @Autowired
    private InvoiceManagementApplicationService invoiceManagementApplicationService;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @MockitoSpyBean
    private InvoiceService invoiceService;

    @MockitoBean
    private PaymentGatewayService paymentGatewayService;

    @MockitoSpyBean
    private InvoiceEventListener invoiceEventListener;

    @Test
    void shouldGenerateInvoiceWithCreditCardAsPayment() {
        UUID customerId = UUID.randomUUID();
        CreditCard creditCard = CreditCardTestDataBuilder.aCreditCard().customerId(customerId).build();
        creditCardRepository.saveAndFlush(creditCard);


        GenerateInvoiceInput generateInvoiceInput = GenerateInvoiceInputTestDataBuilder.anInput().customerId(customerId).build();
        generateInvoiceInput.setPaymentSettings(PaymentSettingsInput.builder().creditCardId(creditCard.getId())
                .method(PaymentMethod.CREDIT_CARD).build());

        UUID invoiceId = invoiceManagementApplicationService.generate(generateInvoiceInput);

        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow();

        Assertions.assertThat(invoice.getStatus()).isEqualTo(InvoiceStatus.UNPAID);
        Assertions.assertThat(invoice.getOrderId()).isEqualTo(generateInvoiceInput.getOrderId());

        Assertions.assertThat(invoice.getVersion()).isEqualTo(0L);
        Assertions.assertThat(invoice.getCreatedAt()).isNotNull();
        Assertions.assertThat(invoice.getCreatedByUserId()).isNotNull();

        Mockito.verify(invoiceService).issue(any(), any(), any(), any());
        Mockito.verify(invoiceEventListener).listen(Mockito.any(InvoiceIssuedEvent.class));
    }

    @Test
    public void shouldGenerateInvoiceWithGatewayBalanceAsPayment() {
        UUID customerId = UUID.randomUUID();
        GenerateInvoiceInput input = GenerateInvoiceInputTestDataBuilder.anInput().build();

        input.setPaymentSettings(
                PaymentSettingsInput.builder()
                        .method(PaymentMethod.GATEWAY_BALANCE)
                        .build()
        );

        UUID invoiceId = invoiceManagementApplicationService.generate(input);

        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow();

        Assertions.assertThat(invoice.getStatus()).isEqualTo(InvoiceStatus.UNPAID);
        Assertions.assertThat(invoice.getOrderId()).isEqualTo(input.getOrderId());

        Mockito.verify(invoiceService).issue(any(), any(), any(), any());
    }

    @Test
    void shouldProcessInvoicePayment() {
        Invoice invoice = InvoiceTestDataBuilder.anInvoice().build();
        invoice.changePaymentSettings(PaymentMethod.GATEWAY_BALANCE, null);
        invoiceRepository.saveAndFlush(invoice);

        Mockito.when(paymentGatewayService.capture(any(PaymentRequest.class))).thenReturn(
                Payment.builder()
                        .gatewayCode("12345")
                        .invoiceId(invoice.getId())
                        .method(invoice.getPaymentSettings().getMethod())
                        .status(PaymentStatus.PAID)
                        .build()
        );

        invoiceManagementApplicationService.processPayment(invoice.getId());
        Invoice paidInvoice = invoiceRepository.findById(invoice.getId()).orElseThrow();
        Assertions.assertThat(paidInvoice.isPaid()).isTrue();

        Mockito.verify(paymentGatewayService).capture(any(PaymentRequest.class));
        Mockito.verify(invoiceService).assignPayment(any(Invoice.class), any(Payment.class));
        Mockito.verify(invoiceEventListener).listen(Mockito.any(InvoicePaidEvent.class));
    }
    @Test
    void shouldProcessInvoicePaymentAndCancelInvoice() {
        Invoice invoice = InvoiceTestDataBuilder.anInvoice().build();
        invoice.changePaymentSettings(PaymentMethod.GATEWAY_BALANCE, null);
        invoiceRepository.saveAndFlush(invoice);

        Mockito.when(paymentGatewayService.capture(any(PaymentRequest.class))).thenReturn(
                Payment.builder()
                        .gatewayCode("12345")
                        .invoiceId(invoice.getId())
                        .method(invoice.getPaymentSettings().getMethod())
                        .status(PaymentStatus.FAILED)
                        .build()
        );

        invoiceManagementApplicationService.processPayment(invoice.getId());
        Invoice paidInvoice = invoiceRepository.findById(invoice.getId()).orElseThrow();
        Assertions.assertThat(paidInvoice.isCanceled()).isTrue();

        Mockito.verify(paymentGatewayService).capture(any(PaymentRequest.class));
        Mockito.verify(invoiceService).assignPayment(any(Invoice.class), any(Payment.class));
        Mockito.verify(invoiceEventListener).listen(Mockito.any(InvoiceCanceledEvent.class));
    }


}