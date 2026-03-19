package com.algaworks.billing.infrastructure.persistence.creditcard.query;

import com.algaworks.billing.application.creditcard.query.CreditCardQueryService;
import com.algaworks.billing.application.creditcard.query.output.CreditCardOutput;
import com.algaworks.billing.domain.model.creditcard.exception.CreditCardNotFoundEntityNotFoundException;
import com.algaworks.billing.domain.model.creditcard.repository.CreditCardRepository;
import com.algaworks.billing.infrastructure.persistence.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CreditCardQueryServiceImpl implements CreditCardQueryService {

    private final CreditCardRepository creditCardRepository;
    private final Mapper mapper;

    @Override
    public CreditCardOutput findOne(UUID customerId, UUID creditCardId) {
        return creditCardRepository.findByCustomerIdAndId(customerId, creditCardId).map(
                c -> mapper.convert(c, CreditCardOutput.class)
        ).orElseThrow(
                () -> new CreditCardNotFoundEntityNotFoundException(creditCardId)
        );

    }

    @Override
    public List<CreditCardOutput> findByCustomer(UUID customerId) {
        return creditCardRepository.findAllByCustomerId(customerId).stream()
                .map(c -> mapper.convert(c, CreditCardOutput.class)).toList();
    }
}
