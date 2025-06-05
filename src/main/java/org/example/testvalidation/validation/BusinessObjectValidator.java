package org.example.testvalidation.validation;

import org.example.testvalidation.exceptions.FailedBusinessValidationException;
import org.example.testvalidation.validation.core.BusinessValidator;
import org.example.testvalidation.validation.core.ValidationResult;
import org.example.testvalidation.validation.core.ValidatorChainFactory;
import org.example.testvalidation.validation.error.dto.ValidationErrorFieldDto;
import org.springframework.stereotype.Component;

// !!! формат возвращаемых сообщений "от балды" (не согласован)
@Component
public class BusinessObjectValidator {
    private final ValidatorChainFactory factory;

    public BusinessObjectValidator(ValidatorChainFactory factory) {
        this.factory = factory;
    }

    /**
     * Валидирует на основании бизнес-логики
     *
     * @param objectToValidate Объект обобщенного типа для валидации
     * @throws FailedBusinessValidationException если валидация не пройдена
     */
    public <T> void validate(T objectToValidate, Class<T> clazz) {
        BusinessValidator<T, ValidationErrorFieldDto> validator = factory.buildValidatorFor(clazz);
        ValidationResult<?> result = validator.validate(objectToValidate);

        if (!result.isValid()) {
            throw new FailedBusinessValidationException(result.getErrors());
        }
    }
}
