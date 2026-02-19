package com.algaworks.billing.application.invoice.service;

import com.algaworks.billing.application.invoice.input.GenerateInvoiceInput;
import com.algaworks.billing.application.invoice.input.PaymentSettingsInput;
import com.algaworks.billing.domain.model.creditcard.CreditCardTestDataBuilder;
import com.algaworks.billing.domain.model.creditcard.entity.CreditCard;
import com.algaworks.billing.domain.model.creditcard.repository.CreditCardRepository;
import com.algaworks.billing.domain.model.invoice.entity.Invoice;
import com.algaworks.billing.domain.model.invoice.enums.InvoiceStatus;
import com.algaworks.billing.domain.model.invoice.enums.PaymentMethod;
import com.algaworks.billing.domain.model.invoice.repository.InvoiceRepository;
import com.algaworks.billing.domain.model.invoice.service.InvoiceService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

        Mockito.verify(invoiceService).issue(any(), any(), any(), any());
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

}