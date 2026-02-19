package com.algaworks.billing.domain.model.creditcard.exception;

import com.algaworks.billing.domain.model.invoice.exception.DomainException;

import java.util.UUID;

public class CreditCardNotFoundException extends DomainException {

    public CreditCardNotFoundException(UUID creditCardId) {
        super(String.format("Credit Card with id %s not exists", creditCardId));
    }

}
