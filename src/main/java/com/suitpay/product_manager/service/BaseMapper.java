package com.suitpay.product_manager.service;

import java.util.Collections;
import java.util.List;

public abstract class BaseMapper<E, D> {

    // Converte uma entidade para um DTO
    public abstract D toDto(E entity);

    // Converte um DTO para uma entidade
    public abstract E toEntity(D dto);

    // Converte uma lista de entidades para uma lista de DTOs
    public List<D> toDtoList(List<E> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream().map(this::toDto).toList();
    }

    // Converte uma lista de DTOs para uma lista de entidades
    public List<E> toEntityList(List<D> dtos) {
        if (dtos == null) return Collections.emptyList();
        return dtos.stream().map(this::toEntity).toList();
    }
}
