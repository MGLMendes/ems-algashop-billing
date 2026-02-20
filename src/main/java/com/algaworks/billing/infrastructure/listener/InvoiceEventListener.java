package com.algaworks.billing.infrastructure.listener;

import com.algaworks.billing.domain.model.invoice.event.InvoiceCanceledEvent;
import com.algaworks.billing.domain.model.invoice.event.InvoiceIssuedEvent;
import com.algaworks.billing.domain.model.invoice.event.InvoicePaidEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InvoiceEventListener {
    @EventListener
    public void listen(InvoiceIssuedEvent event) {

    }

    @EventListener
    public void listen(InvoicePaidEvent event) {

    }

    @EventListener
    public void listen(InvoiceCanceledEvent event) {

    }
}
