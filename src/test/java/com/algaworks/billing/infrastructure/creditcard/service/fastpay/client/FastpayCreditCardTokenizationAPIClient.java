package com.algaworks.billing.infrastructure.creditcard.service.fastpay.client;

import com.algaworks.billing.infrastructure.creditcard.service.fastpay.input.FastpayTokenizationInput;
import com.algaworks.billing.infrastructure.creditcard.service.fastpay.model.TokenizedCreditCardModel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface FastpayCreditCardTokenizationAPIClient {
    @PostExchange("/api/v1/public/tokenized-cards")
    TokenizedCreditCardModel tokenized(@RequestBody FastpayTokenizationInput input);
}
