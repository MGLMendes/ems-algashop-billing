package com.algaworks.billing.domain.model.creditcard;

import com.algaworks.billing.domain.utility.IdGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;


@Entity
@Setter(AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CreditCard {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private OffsetDateTime createdAt;
    private UUID customerId;

    private String lastNumbers;
    private String brand;
    private Integer expirationMonth;
    private Integer expirationYear;

    private String gatewayCode;

    public static CreditCard brandNew(UUID customerId, String lastNumbers, String brand,
                                      Integer expirationMonth, Integer expirationYear,
                                      String gatewayCreditCardCode) {
        Objects.requireNonNull(customerId);
        Objects.requireNonNull(expirationMonth);
        Objects.requireNonNull(expirationYear);

        if (StringUtils.isAnyBlank(lastNumbers, brand, gatewayCreditCardCode)) {
            throw new IllegalArgumentException();
        }

        return new CreditCard(
                IdGenerator.generateTimeBasedUUID(),
                OffsetDateTime.now(),
                customerId,
                lastNumbers,
                brand,
                expirationMonth,
                expirationYear,
                gatewayCreditCardCode);
    }

    public void setGatewayCode(String gatewayCode) {
        if (StringUtils.isBlank(gatewayCode)) {
            throw new IllegalArgumentException();
        }
        this.gatewayCode = gatewayCode;
    }
}
