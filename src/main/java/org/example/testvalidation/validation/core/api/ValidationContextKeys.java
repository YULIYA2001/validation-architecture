package org.example.testvalidation.validation.core.api;

import org.example.testvalidation.validation.validators.CitizenshipExistsValidator;

/**
 * Перечисление ключей контекста валидации
 * <p>
 *     Значения данного перечисления используются в {@link ValidationContext}, чтобы определить,
 *     какие валидаторы следует активировать при проверке объекта.
 * <p>
 *     Каждый ключ связан с конкретным именем поля в DTO-объекте.
 */
public enum ValidationContextKeys {
    /**
     * Ключ, активирующий бизнес-валидацию наличия гражданства для поля с именем {@code "citizenship"}.
     * <p>
     *     Используется, например, в {@link CitizenshipExistsValidator}
     */
    CITIZENSHIP_EXISTS("citizenship"),
    FACULTY_EXISTS("faculty"),
    LAST_NAME("lastName"),
    FIRST_NAME("firstName"),
    MOBILE_PHONE_BEL("contact.value");

    private final String fieldName;

    ValidationContextKeys(final String id) {
        this.fieldName = id;
    }

    public String getFieldName() {
        return fieldName;
    }
}
