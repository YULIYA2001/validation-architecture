package org.example.testvalidation.validation.core.api;

import java.util.Set;

/**
 * Интерфейс, помечающий валидаторы, которые могут быть включены или исключены из валидации на основе ключей контекста.
 */
public interface ConditionalValidator {
    /**
     * Возвращает набор ключей контекста, с которыми связан данный валидатор.
     * Если валидация проводится с этим ключом — валидатор будет активирован.
     */
    Set<ValidationContextKeys> getKeys();
}