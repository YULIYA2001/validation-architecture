package org.example.testvalidation.validation.core.api;

import java.util.Set;

/**
 * Контекст валидации, определяющий, какие ключи активны (какие валидаторы должны быть применены)
 * при валидации бизнес-объекта
 */
public class ValidationContext {
    private final Set<ValidationContextKeys> validationKeys;

    private ValidationContext(Set<ValidationContextKeys> validationKeys) {
        this.validationKeys = validationKeys;
    }

    /**
     * Создаёт новый контекст {@link ValidationContext} с заданным набором ключей
     */
    public static ValidationContext of(ValidationContextKeys... keys) {
        return new ValidationContext(Set.of(keys));
    }

    /**
     * Проверяет, должен ли валидатор быть применён в данном контексте
     * <p>
     *     Если валидатор реализует интерфейс {@link ConditionalValidator}, проверяется пересечение его ключей
     *     с ключами текущего контекста. Если пересечение есть — валидация разрешена.
     * <p>
     *     Если валидатор не является {@code ConditionalValidator}, он считается универсальным
     *     и допускается к выполнению без ограничений (валидация будет всегда)
     */
    public boolean allows(BusinessValidator<?> validator) {
        if (validator instanceof ConditionalValidator conditional) {
            return conditional.getKeys().stream().anyMatch(validationKeys::contains);
        }
        return true;
    }
}
