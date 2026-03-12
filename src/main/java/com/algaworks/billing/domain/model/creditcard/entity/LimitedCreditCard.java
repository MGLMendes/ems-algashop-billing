package com.algaworks.billing.domain.model.creditcard.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LimitedCreditCard {
    private String gatewayCode;
    private String lastNumbers;
    private String brand;
    private Integer expirationMonth;
    private Integer expirationYear;

}
