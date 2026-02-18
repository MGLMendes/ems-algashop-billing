package com.algaworks.billing.domain.model.invoice;

import com.algaworks.billing.domain.model.invoice.enums.PaymentMethod;
import lombok.*;

import java.util.UUID;


@Setter(AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentSettings {

    @EqualsAndHashCode.Include
    private UUID id;
    private UUID creditCardId;
    private String gatewayCode;
    private PaymentMethod method;


}
