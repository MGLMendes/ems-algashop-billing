package com.algaworks.billing.application.invoice.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenerateInvoiceInput{
    private String orderId;
    @NotNull
    private UUID customerId;
    @Valid
    @NotNull
    private PaymentSettingsInput paymentSettings;
    @Valid
    @NotNull
    private PayerData payer;
    @Valid
    @NotNull
    private List<LineItemInput> items;
}