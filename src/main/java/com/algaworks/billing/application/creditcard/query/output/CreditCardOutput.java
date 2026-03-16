package com.algaworks.billing.application.creditcard.query.output;

import lombok.Data;

import java.util.UUID;

@Data
public class CreditCardOutput {
    private UUID id;
    private String lastNumbers;
    private Integer expMonth;
    private Integer expYear;
}
