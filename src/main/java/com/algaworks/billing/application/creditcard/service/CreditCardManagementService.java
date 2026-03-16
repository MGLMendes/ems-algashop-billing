package com.algaworks.billing.application.creditcard.service;

import com.algaworks.billing.application.creditcard.query.input.TokenizedCreditCardInput;
import com.algaworks.billing.domain.model.creditcard.entity.CreditCard;
import com.algaworks.billing.domain.model.creditcard.entity.LimitedCreditCard;
import com.algaworks.billing.domain.model.creditcard.exception.CreditCardNotFoundException;
import com.algaworks.billing.domain.model.creditcard.repository.CreditCardRepository;
import com.algaworks.billing.domain.model.creditcard.service.CreditCardProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreditCardManagementService {

    private final CreditCardRepository creditCardRepository;
    private final CreditCardProviderService creditCardProviderService;

    @Transactional
    public UUID register(TokenizedCreditCardInput input) {
        LimitedCreditCard limitedCreditCard = creditCardProviderService.register(input.getCustomerId(), input.getTokenizedCard());
        CreditCard creditCard = CreditCard.brandNew(
                input.getCustomerId(),
                limitedCreditCard.getLastNumbers(),
                limitedCreditCard.getBrand(),
                limitedCreditCard.getExpirationMonth(),
                limitedCreditCard.getExpirationYear(),
                limitedCreditCard.getGatewayCode()
        );

        creditCardRepository.saveAndFlush(creditCard);
        return creditCard.getId();
    }

    @Transactional
    public void delete(UUID customerId, UUID creditCardId) {
        CreditCard creditCard = creditCardRepository.findByCustomerIdAndId(customerId, creditCardId).orElseThrow(
                () -> new CreditCardNotFoundException(creditCardId)
        );

        creditCardRepository.delete(creditCard);

        creditCardProviderService.delete(creditCard.getGatewayCode());
    }
}
