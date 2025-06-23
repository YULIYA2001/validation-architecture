package org.example.testvalidation.validation.validators;

import java.util.Set;
import org.example.testvalidation.repositories.CommonTestRepository;
import org.example.testvalidation.validation.core.api.ValidationContextKeys;
import org.example.testvalidation.validation.core.api.ValidationFields;
import org.example.testvalidation.validation.core.api.ValidationResult;
import org.example.testvalidation.validation.error.dto.ValidationErrorFieldDto;
import org.example.testvalidation.validation.utils.FieldExtractor;
import org.example.testvalidation.validation.utils.ValidationMessages;
import org.example.testvalidation.validation.validators.base.AbstractConditionalValidator;
import org.springframework.stereotype.Component;

@Component
public class StudentExistsConditionalValidator extends AbstractConditionalValidator<ValidationErrorFieldDto> {
    private final CommonTestRepository repo;

    public StudentExistsConditionalValidator(CommonTestRepository repo) {
        this.repo = repo;
    }

    @Override
    public ValidationResult<ValidationErrorFieldDto> validate(Object dto) {
        Object rawLastName = FieldExtractor.findFieldByName(dto, ValidationFields.LAST_NAME.getName());
        Object rawFirstName = FieldExtractor.findFieldByName(dto, ValidationFields.FIRST_NAME.getName());
        Object rawGroup = FieldExtractor.findFieldByName(dto, ValidationFields.GROUP_NUMBER.getName());

        String lastName  = null;
        String firstName = null;
        String groupNumber = null;
        if (rawLastName instanceof String ln && rawFirstName instanceof String fn && rawGroup instanceof String gn) {
            lastName = ln;
            firstName = fn;
            groupNumber = gn;
        }

        ValidationResult<ValidationErrorFieldDto> result = ValidationResult.ok();
        if (lastName != null && firstName != null && groupNumber != null
                && repo.studentExists(lastName, firstName, groupNumber)) {
            result.addError(new ValidationErrorFieldDto(
                    String.format(
                            "%s %s %s",
                            ValidationFields.LAST_NAME.getName(),
                            ValidationFields.FIRST_NAME.getName(),
                            ValidationFields.GROUP_NUMBER.getName()
                    ),
                    String.format("Студент: %s %s %s", lastName, firstName, groupNumber),
                    ValidationMessages.ALREADY_EXISTS_IN_DB)
            );
        }

        return result;
    }

    @Override
    public Set<ValidationContextKeys> getKeys() {
        return Set.of(ValidationContextKeys.STUDENT);
    }
}
