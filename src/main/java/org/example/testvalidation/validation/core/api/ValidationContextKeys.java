package org.example.testvalidation.validation.core.api;

import org.example.testvalidation.validation.core.ValidationContext;
import org.example.testvalidation.validation.validators.CitizenshipExistsValidator;

/**
 * Перечисление ключей контекста валидации, используемых для включения или определённых валидаторов.
 * <p>
 * Значения данного перечисления используются в {@link ValidationContext},
 * чтобы определить, какие валидаторы следует активировать при проверке объекта.
 *
 * <p><b>Пример использования:</b>
 * <pre>{@code
 * ValidationContext context = ValidationContext.of(ValidationContextKeys.CITIZENSHIP_EXISTS);
 * }</pre>
 */
public enum ValidationContextKeys {
    /**
     * Ключ, активирующий бизнес-валидацию наличия гражданства для поля с именем {@code "citizenship"}.
     * <p>
     * Используется, например, в {@link CitizenshipExistsValidator}
     */
    CITIZENSHIP_EXISTS("citizenship"),
    FACULTY_EXISTS("faculty");

    private final String fieldName;

    ValidationContextKeys(final String id) {
        this.fieldName = id;
    }

    public String getFieldName() {
        return fieldName;
    }
}
