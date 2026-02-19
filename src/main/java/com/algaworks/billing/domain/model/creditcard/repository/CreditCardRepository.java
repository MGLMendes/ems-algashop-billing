package com.algaworks.billing.domain.model.creditcard.repository;

import com.algaworks.billing.domain.model.creditcard.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CreditCardRepository extends JpaRepository<CreditCard, UUID> {
    boolean existsByIdAndCustomerId(UUID creditCardId, UUID customerId);
}
