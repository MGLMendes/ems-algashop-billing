package com.algaworks.billing.application.invoice.query;

import com.algaworks.billing.application.invoice.output.InvoiceOutput;

public interface InvoiceQueryService {
    InvoiceOutput findByOrderId(String orderId);
}
