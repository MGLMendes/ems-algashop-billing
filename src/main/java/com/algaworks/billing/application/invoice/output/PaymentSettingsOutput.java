package com.algaworks.billing.application.invoice.output;

import com.algaworks.billing.domain.model.invoice.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSettingsOutput {
    private UUID id;
    private UUID creditCardId;
    private PaymentMethod method;
}
