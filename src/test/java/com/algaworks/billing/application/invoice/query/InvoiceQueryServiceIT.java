package com.algaworks.billing.application.invoice.query;

import com.algaworks.billing.application.invoice.AbstractApplicationIT;
import com.algaworks.billing.application.invoice.output.InvoiceOutput;
import com.algaworks.billing.domain.model.invoice.InvoiceTestDataBuilder;
import com.algaworks.billing.domain.model.invoice.entity.Invoice;
import com.algaworks.billing.domain.model.invoice.repository.InvoiceRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class InvoiceQueryServiceIT extends AbstractApplicationIT {

    @Autowired
    private InvoiceQueryService invoiceQueryService;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Test
    void shouldFindByOrderId() {
        Invoice invoice = InvoiceTestDataBuilder.anInvoice().build();
        invoiceRepository.saveAndFlush(invoice);
        InvoiceOutput invoiceOutput = invoiceQueryService.findByOrderId(invoice.getOrderId());

        Assertions.assertThat(invoiceOutput.getId()).isEqualTo(invoice.getId());

    }
}