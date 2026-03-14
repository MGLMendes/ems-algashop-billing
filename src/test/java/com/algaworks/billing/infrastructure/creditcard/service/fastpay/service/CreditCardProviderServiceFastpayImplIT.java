package com.algaworks.billing.infrastructure.creditcard.service.fastpay.service;

import com.algaworks.billing.domain.model.creditcard.entity.LimitedCreditCard;
import com.algaworks.billing.infrastructure.creditcard.service.fastpay.CreditCardProviderServiceFastpayImpl;
import com.algaworks.billing.infrastructure.creditcard.service.fastpay.client.FastpayCreditCardTokenizationAPIClient;
import com.algaworks.billing.infrastructure.creditcard.service.fastpay.config.FastpayCreditCardTokenizationAPIClientConfig;
import com.algaworks.billing.infrastructure.creditcard.service.fastpay.input.FastpayTokenizationInput;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.Year;
import java.util.Optional;
import java.util.UUID;


@SpringBootTest
@Import(FastpayCreditCardTokenizationAPIClientConfig.class)
class CreditCardProviderServiceFastpayImplIT {

    @Autowired
    private CreditCardProviderServiceFastpayImpl creditCardProvider;

    @Autowired
    private FastpayCreditCardTokenizationAPIClient tokenizationAPIClient;

    private static final UUID validCustomerId = UUID.randomUUID();

    private static final String validCreditCard = "4622943127011022";
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

    private LimitedCreditCard registerCard() {
        FastpayTokenizationInput input = FastpayTokenizationInput.builder()
                .number(validCreditCard)
                .cvv("234")
                .holderName("Miguel")
                .holderDocument("22244411142")
                .expMonth(3)
                .expYear(Year.now().plusYears(5).getValue())
                .build();
        var tokenizedCard = tokenizationAPIClient.tokenized(input);
        LimitedCreditCard limitedCreditCard = creditCardProvider.register(validCustomerId, tokenizedCard.getTokenizedCard());
        return limitedCreditCard;
    }
}