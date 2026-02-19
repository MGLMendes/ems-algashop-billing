package com.algaworks.billing.infrastructure.persistence.invoice.query;

import com.algaworks.billing.application.invoice.output.InvoiceOutput;
import com.algaworks.billing.application.invoice.query.InvoiceQueryService;
import com.algaworks.billing.domain.model.invoice.entity.Invoice;
import com.algaworks.billing.domain.model.invoice.exception.InvoiceNotFoundException;
import com.algaworks.billing.domain.model.invoice.repository.InvoiceRepository;
import com.algaworks.billing.infrastructure.persistence.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InvoiceQueryServiceImpl implements InvoiceQueryService {

    private final InvoiceRepository invoiceRepository;
    private final Mapper mapper;

    @Override
    public InvoiceOutput findByOrderId(String orderId) {
        Invoice invoice = invoiceRepository.findByOrderId(orderId).orElseThrow(
                InvoiceNotFoundException::new
        );
        return mapper.convert(invoice, InvoiceOutput.class);
    }
}
