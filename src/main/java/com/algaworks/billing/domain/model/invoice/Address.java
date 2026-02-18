package com.algaworks.billing.domain.model.invoice;

import com.algaworks.billing.domain.validator.FieldValidations;
import lombok.*;

@EqualsAndHashCode
@Setter(AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String zipCode;

    @Builder
    public Address(String street, String number, String complement, String neighborhood,
                   String city, String state, String zipCode) {
        FieldValidations.requiresNonBlank(street);
        FieldValidations.requiresNonBlank(number);
        FieldValidations.requiresNonBlank(neighborhood);
        FieldValidations.requiresNonBlank(city);
        FieldValidations.requiresNonBlank(state);
        FieldValidations.requiresNonBlank(zipCode);
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }
}
