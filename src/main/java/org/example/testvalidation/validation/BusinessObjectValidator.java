package org.example.testvalidation.validation;

import org.example.testvalidation.exceptions.FailedBusinessValidationException;
import org.example.testvalidation.validation.core.api.BusinessValidator;
import org.example.testvalidation.validation.core.ValidationContext;
import org.example.testvalidation.validation.core.api.ValidationResult;
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
     */
    public <T> void validate(T objectToValidate, ValidationContext context) {
        BusinessValidator<ValidationErrorFieldDto> validator = factory.buildValidatorFor(context);
        ValidationResult<?> result = validator.validate(objectToValidate);

        if (!result.isValid()) {
            throw new FailedBusinessValidationException(result.getErrors());
        }
    }
}
