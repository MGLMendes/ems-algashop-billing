package com.algaworks.billing.infrastructure;

import com.algaworks.billing.domain.model.creditcard.entity.LimitedCreditCard;
import com.algaworks.billing.infrastructure.creditcard.service.fastpay.CreditCardProviderServiceFastpayImpl;
import com.algaworks.billing.infrastructure.creditcard.service.fastpay.client.FastpayCreditCardTokenizationAPIClient;
import com.algaworks.billing.infrastructure.creditcard.service.fastpay.config.FastpayCreditCardTokenizationAPIClientConfig;
import com.algaworks.billing.infrastructure.creditcard.service.fastpay.input.FastpayTokenizationInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.time.Year;
import java.util.UUID;

@Import(FastpayCreditCardTokenizationAPIClientConfig.class)
public abstract class AbstractFastpayIT {

    @Autowired
    protected FastpayCreditCardTokenizationAPIClient tokenizationAPIClient;;

    @Autowired
    protected CreditCardProviderServiceFastpayImpl creditCardProvider;

    protected static final UUID validCustomerId = UUID.randomUUID();
    protected static final String alwaysPaidCardNumber = "4622943127011022";

    protected LimitedCreditCard registerCard() {
        FastpayTokenizationInput input = FastpayTokenizationInput.builder()
                .number(alwaysPaidCardNumber)
                .cvv("234")
                .holderName("Miguel")
                .holderDocument("22244411142")
                .expMonth(3)
                .expYear(Year.now().plusYears(5).getValue())
                .build();
        var tokenizedCard = tokenizationAPIClient.tokenized(input);
        return creditCardProvider.register(validCustomerId, tokenizedCard.getTokenizedCard());
    }
}
