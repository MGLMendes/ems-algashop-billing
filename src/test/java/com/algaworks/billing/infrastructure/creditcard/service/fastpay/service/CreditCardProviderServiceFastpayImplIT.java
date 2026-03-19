package com.algaworks.billing.infrastructure.creditcard.service.fastpay.service;

import com.algaworks.billing.domain.model.creditcard.entity.LimitedCreditCard;
import com.algaworks.billing.infrastructure.AbstractFastpayIT;
import com.algaworks.billing.infrastructure.creditcard.service.fastpay.config.FastpayCreditCardTokenizationAPIClientConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;


@SpringBootTest
@Import(FastpayCreditCardTokenizationAPIClientConfig.class)
class CreditCardProviderServiceFastpayImplIT extends AbstractFastpayIT {

    @BeforeAll
    static void beforeAll() {
        AbstractFastpayIT.startMock();
    }

    @AfterAll
    static void afterAll() {
        AbstractFastpayIT.stopMock();
    }

    @Test
    void shouldRegisterCreditCard() {
        LimitedCreditCard limitedCreditCard = registerCard();

        Assertions.assertThat(limitedCreditCard.getGatewayCode()).isNotBlank();
    }

    @Test
    void shouldFindRegisteredCreditCard() {
        LimitedCreditCard limitedCreditCard = registerCard();
        LimitedCreditCard found = creditCardProvider.findById(limitedCreditCard.getGatewayCode()).orElseThrow();
        Assertions.assertThat(found.getGatewayCode()).isEqualTo(limitedCreditCard.getGatewayCode());
    }

    @Test
    void shouldDeleteRegisteredCreditCard() {
        LimitedCreditCard limitedCreditCard = registerCard();
        creditCardProvider.delete(limitedCreditCard.getGatewayCode());
        Optional<LimitedCreditCard> possibleCreditCard = creditCardProvider.findById(limitedCreditCard.getGatewayCode());
        Assertions.assertThat(possibleCreditCard.isEmpty());

    }
}