package com.algaworks.billing.domain.model.invoice.entity;

import com.algaworks.billing.domain.validator.FieldValidations;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@EqualsAndHashCode
@Setter(AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class LineItem {
    private Integer number;
    private String name;
    private BigDecimal amount;

    @Builder
    public LineItem(Integer number, String name, BigDecimal amount) {
        Objects.requireNonNull(number);
        Objects.requireNonNull(amount);
        FieldValidations.requiresNonBlank(name);

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException();
        }

        if (number <= 0) {
            throw new IllegalArgumentException();
        }
        this.number = number;
        this.name = name;
        this.amount = amount;
    }
}
