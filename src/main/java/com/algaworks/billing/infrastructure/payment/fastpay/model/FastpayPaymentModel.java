package com.algaworks.billing.infrastructure.payment.fastpay.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FastpayPaymentModel {
    private String id;
    private String referenceCode;
    private String status;
    private String method;
    private BigDecimal totalAmount;
}
