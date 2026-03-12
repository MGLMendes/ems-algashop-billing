package com.algaworks.billing.domain.model.creditcard.service;

import com.algaworks.billing.domain.model.creditcard.entity.LimitedCreditCard;

import java.util.Optional;
import java.util.UUID;

public interface CreditCardProviderService {

    LimitedCreditCard register(UUID customerId, String tokenizedCard);

    Optional<LimitedCreditCard> findById(String gatewayCode);

    void delete(String gatewayCode);
}
