package org.example.testvalidation.validation.validators;

import java.util.Set;
import org.example.testvalidation.repositories.CommonTestRepository;
import org.example.testvalidation.validation.core.api.ValidationContextKeys;
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
        Object rawLastName = FieldExtractor.findFieldByName(dto, ValidationContextKeys.LAST_NAME.getFieldName());
        Object rawFirstName = FieldExtractor.findFieldByName(dto, ValidationContextKeys.FIRST_NAME.getFieldName());
        Object rawGroup = FieldExtractor.findFieldByName(dto, ValidationContextKeys.GROUP_NUMBER.getFieldName());

        String lastName  = null;
        String firstName = null;
        String groupNumber = null;
        if (rawLastName instanceof String ln && rawFirstName instanceof String fn && rawFirstName instanceof String gn) {
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
                            ValidationContextKeys.LAST_NAME.getFieldName(),
                            ValidationContextKeys.FIRST_NAME.getFieldName(),
                            ValidationContextKeys.GROUP_NUMBER.getFieldName()
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
