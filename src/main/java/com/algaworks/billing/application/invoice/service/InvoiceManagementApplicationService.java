package com.algaworks.billing.application.invoice.service;

import com.algaworks.billing.application.invoice.input.*;
import com.algaworks.billing.domain.model.creditcard.exception.CreditCardNotFoundException;
import com.algaworks.billing.domain.model.creditcard.repository.CreditCardRepository;
import com.algaworks.billing.domain.model.invoice.entity.Address;
import com.algaworks.billing.domain.model.invoice.entity.Invoice;
import com.algaworks.billing.domain.model.invoice.entity.LineItem;
import com.algaworks.billing.domain.model.invoice.entity.Payer;
import com.algaworks.billing.domain.model.invoice.payment.service.PaymentGatewayService;
import com.algaworks.billing.domain.model.invoice.repository.InvoiceRepository;
import com.algaworks.billing.domain.model.invoice.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceManagementApplicationService {

    private final PaymentGatewayService paymentGatewayService;
    private final InvoiceService invoiceService;
    private final InvoiceRepository invoiceRepository;
    private final CreditCardRepository creditCardRepository;

    @Transactional
    public UUID generate(GenerateInvoiceInput input) {
        PaymentSettingsInput paymentSettingsInput = input.getPaymentSettings();
        verifyCreditCardId(input.getPaymentSettings().getCreditCardId(), input.getCustomerId());

        Payer payer =convertToPayer(input.getPayer());

        Set<LineItem> items = convertToLineItems(input.getItems());

        Invoice invoice = invoiceService.issue(input.getOrderId(), input.getCustomerId(), payer, items);
        invoice.changePaymentSettings(paymentSettingsInput.getMethod(), paymentSettingsInput.getCreditCardId());

        invoiceRepository.saveAndFlush(invoice);
        return invoice.getId();
    }

    private Set<LineItem> convertToLineItems(Set<LineItemInput> lineItemInputs) {
        Set<LineItem> lineItems = new LinkedHashSet<>();
        int itemNumber = 1;
        for (LineItemInput lineItemInput : lineItemInputs) {
            lineItems.add(LineItem.builder()
                            .number(itemNumber).name(lineItemInput.getName()).amount(lineItemInput.getAmount())
                    .build());
            itemNumber++;
        }
        return lineItems;
    }

    private Payer convertToPayer(PayerData payerData) {
        AddressData addressData = payerData.getAddress();
        return Payer.builder()
                .fullName(payerData.getFullName())
                .document(payerData.getDocument())
                .phone(payerData.getPhone())
                .email(payerData.getEmail())
                .address(Address.builder()
                        .street(addressData.getStreet())
                        .number(addressData.getNumber())
                        .complement(addressData.getComplement())
                        .neighborhood(addressData.getNeighborhood())
                        .city(addressData.getCity())
                        .state(addressData.getState())
                        .zipCode(addressData.getZipCode())
                        .build())
                .build();
    }

    private void verifyCreditCardId(UUID creditCardId, UUID customerId) {
        if (creditCardId != null && !creditCardRepository.existsByIdAndCustomerId(creditCardId, customerId)) {
            throw new CreditCardNotFoundException(creditCardId);
        }
    }
}
