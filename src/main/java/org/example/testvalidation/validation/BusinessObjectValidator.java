package org.example.testvalidation.validation;

import org.example.testvalidation.exceptions.FailedBusinessValidationException;
import org.example.testvalidation.validation.core.BusinessValidator;
import org.example.testvalidation.validation.core.ValidationResult;
import org.example.testvalidation.validation.core.ValidatorChainFactory;
import org.example.testvalidation.validation.error.dto.ValidationErrorFieldDto;
import org.springframework.stereotype.Component;


@Component
public class BusinessObjectValidator {
    private final ValidatorChainFactory factory;

    public BusinessObjectValidator(ValidatorChainFactory factory) {
        this.factory = factory;
    }

    /**
     * Валидирует на основании бизнес-логики
     *
     * @param objectToValidate объект, который необходимо провалидировать
     * @throws FailedBusinessValidationException если объект не прошёл бизнес-валидацию;
     *                                           содержит список ошибок валидации
     */
    public <T> void validate(T objectToValidate) {
        BusinessValidator<ValidationErrorFieldDto> validator = factory.buildValidatorFor(objectToValidate);
        ValidationResult<?> result = validator.validate(objectToValidate);

        if (!result.isValid()) {
            throw new FailedBusinessValidationException(result.getErrors());
        }
    }
}
