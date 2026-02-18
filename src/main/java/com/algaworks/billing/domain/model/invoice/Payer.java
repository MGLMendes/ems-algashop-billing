package com.algaworks.billing.domain.model.invoice;

import lombok.*;

@EqualsAndHashCode
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Payer {
    private String fullName;
    private String document;
    private String phone;
    private String email;
    private Address address;
}
