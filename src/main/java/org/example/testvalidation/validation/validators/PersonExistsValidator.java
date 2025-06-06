package org.example.testvalidation.validation.validators;

import org.example.testvalidation.repositories.CommonTestRepository;
import org.example.testvalidation.validation.core.api.BusinessValidator;
import org.example.testvalidation.validation.core.api.ValidationContextKeys;
import org.example.testvalidation.validation.core.api.ValidationResult;
import org.example.testvalidation.validation.error.dto.ValidationErrorFieldDto;
import org.example.testvalidation.validation.utils.FieldExtractor;
import org.example.testvalidation.validation.utils.ValidationMessages;
import org.springframework.stereotype.Component;

@Component
public class PersonExistsValidator implements BusinessValidator<ValidationErrorFieldDto> {
    private final CommonTestRepository repo;

    public PersonExistsValidator(CommonTestRepository repo) {
        this.repo = repo;
    }
    @Override
    public ValidationResult<ValidationErrorFieldDto> validate(Object dto) {
        Object rawLastName = FieldExtractor.findFieldByName(dto, ValidationContextKeys.LAST_NAME.getFieldName());
        Object rawFirstName = FieldExtractor.findFieldByName(dto, ValidationContextKeys.FIRST_NAME.getFieldName());

        String lastName  = null;
        String firstName = null;
        if (rawLastName instanceof String ln && rawFirstName instanceof String fn) {
            lastName = ln;
            firstName = fn;
        }

        ValidationResult<ValidationErrorFieldDto> result = ValidationResult.ok();
        if (lastName != null && firstName != null && repo.personExists(lastName, firstName)) {
            result.addError(new ValidationErrorFieldDto(
                    String.format(
                            "%s %s",
                            ValidationContextKeys.LAST_NAME.getFieldName(),
                            ValidationContextKeys.FIRST_NAME.getFieldName()
                    ),
                    String.format("Человек: %s %s", lastName, firstName),
                    ValidationMessages.ALREADY_EXISTS_IN_DB)
            );
        }

        return result;
    }
}
