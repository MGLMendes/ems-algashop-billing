package com.algaworks.billing.application.invoice.input;

import com.algaworks.billing.domain.model.invoice.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSettingsInput {
	private PaymentMethod method;
	private UUID creditCardId;
}