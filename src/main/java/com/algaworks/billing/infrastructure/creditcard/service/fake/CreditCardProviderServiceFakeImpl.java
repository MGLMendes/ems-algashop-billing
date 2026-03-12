package com.algaworks.billing.infrastructure.creditcard.service.fake;

import com.algaworks.billing.domain.model.creditcard.entity.LimitedCreditCard;
import com.algaworks.billing.domain.model.creditcard.service.CreditCardProviderService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Optional;
import java.util.UUID;

@Service
@ConditionalOnProperty(name = "algashop.integrations.payment.provider", havingValue = "FAKE")
public class CreditCardProviderServiceFakeImpl implements CreditCardProviderService {
    @Override
    public LimitedCreditCard register(UUID customerId, String tokenizedCard) {
        return fakeCard();
    }

    @Override
    public Optional<LimitedCreditCard> findById(String gatewayCode) {
        return Optional.of(fakeCard());
    }

    @Override
    public void delete(String gatewayCode) {

    }

    private static LimitedCreditCard fakeCard() {
        return LimitedCreditCard.builder()
                .brand("Visa")
                .expirationMonth(1)
                .expirationYear(Year.now().getValue() + 5)
                .gatewayCode(UUID.randomUUID().toString())
                .lastNumbers("1234")
                .build();
    }
}
