package org.example.testvalidation.validation.validators;

import org.example.testvalidation.repositories.CommonTestRepository;
import org.example.testvalidation.validation.core.api.BusinessValidator;
import org.example.testvalidation.validation.core.api.ValidationFields;
import org.example.testvalidation.validation.core.api.ValidationResult;
import org.example.testvalidation.validation.error.dto.ValidationErrorFieldDto;
import org.example.testvalidation.validation.utils.FieldExtractor;
import org.example.testvalidation.validation.utils.ValidationMessages;
import org.springframework.stereotype.Component;

/**
 * Безусловный (не зависит от контекста) валидатор проверки существования человека в "БД"
 * Автоматически включается в проверку любого dto, в котором есть (поиск рекурсивный) проверяемые в
 * валидаторе поля (LAST_NAME, FIRST_NAME)
 */
@Component
public class PersonExistsValidator implements BusinessValidator<ValidationErrorFieldDto> {
    private final CommonTestRepository repo;

    public PersonExistsValidator(CommonTestRepository repo) {
        this.repo = repo;
    }
    @Override
    public ValidationResult<ValidationErrorFieldDto> validate(Object dto) {
        Object rawLastName = FieldExtractor.findFieldByName(dto, ValidationFields.LAST_NAME.getName());
        Object rawFirstName = FieldExtractor.findFieldByName(dto, ValidationFields.FIRST_NAME.getName());

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
                            ValidationFields.LAST_NAME.getName(),
                            ValidationFields.FIRST_NAME.getName()
                    ),
                    String.format("Человек: %s %s", lastName, firstName),
                    ValidationMessages.ALREADY_EXISTS_IN_DB)
            );
        }

        return result;
    }
}
