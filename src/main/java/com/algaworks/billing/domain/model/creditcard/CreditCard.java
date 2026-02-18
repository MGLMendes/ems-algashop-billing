package com.algaworks.billing.domain.model.creditcard;

import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import java.util.UUID;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CreditCard {

    @EqualsAndHashCode.Include
    private UUID id;
    private OffsetDateTime createdAt;
    private UUID customerId;

    private String lastNumbers;
    private String brand;
    private Integer expirationMonth;
    private Integer expirationYear;
    private String gatewayCode;
}
