package com.algaworks.billing.domain.model.invoice.exception;

import java.util.UUID;

public class InvoiceNotFoundException extends DomainException {

    public InvoiceNotFoundException(UUID invoiceId) {
        super(String.format("Invoice with id %s not exists", invoiceId));
    }

}
