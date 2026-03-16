package com.algaworks.billing.application.creditcard.query;


import com.algaworks.billing.application.creditcard.query.output.CreditCardOutput;

import java.util.List;
import java.util.UUID;

public interface CreditCardQueryService {

    CreditCardOutput findOne(UUID customerId, UUID creditCardId);

    List<CreditCardOutput> findByCustomer(UUID customerId);
}
