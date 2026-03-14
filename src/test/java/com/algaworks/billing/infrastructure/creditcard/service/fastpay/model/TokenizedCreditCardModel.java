package com.algaworks.billing.infrastructure.creditcard.service.fastpay.model;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class TokenizedCreditCardModel {
    private String tokenizedCard;
    private OffsetDateTime expiresAt;
}
