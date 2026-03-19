package com.algaworks.billing.domain.model.creditcard.exception;

import com.algaworks.billing.domain.model.invoice.exception.DomainEntityNotFoundException;

import java.util.UUID;

public class CreditCardNotFoundEntityNotFoundException extends DomainEntityNotFoundException {

    public CreditCardNotFoundEntityNotFoundException(UUID creditCardId) {
        super(String.format("Credit Card with id %s not exists", creditCardId));
    }

}
