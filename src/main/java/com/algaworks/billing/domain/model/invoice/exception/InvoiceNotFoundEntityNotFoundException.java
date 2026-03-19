package com.algaworks.billing.domain.model.invoice.exception;

import java.util.UUID;

public class InvoiceNotFoundEntityNotFoundException extends DomainEntityNotFoundException {

    public InvoiceNotFoundEntityNotFoundException(UUID invoiceId) {
        super(String.format("Invoice with id %s not exists", invoiceId));
    }

    public InvoiceNotFoundEntityNotFoundException() {
        super("Invoice not found");
    }

}
