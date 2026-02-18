package com.algaworks.billing.domain.model.invoice;

import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode
@Setter(AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LineItem {

    private Integer number;
    private String name;
    private BigDecimal amount;
}
