package com.algaworks.billing.infrastructure.persistence.mapper;

public interface Mapper {

    <T> T convert(Object o, Class<T> destinationClass);
}
