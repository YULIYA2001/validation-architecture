package org.example.testvalidation.validation.validators;

import java.util.Set;
import org.example.testvalidation.repositories.CommonTestRepository;
import org.example.testvalidation.validation.core.api.ValidationContextKeys;
import org.example.testvalidation.validation.core.api.ValidationResult;
import org.example.testvalidation.validation.error.dto.ValidationErrorFieldDto;
import org.example.testvalidation.validation.utils.FieldExtractor;
import org.example.testvalidation.validation.utils.ValidationMessages;
import org.springframework.stereotype.Component;

@Component
public class FacultyExistsValidator extends AbstractValidator<ValidationErrorFieldDto> {
    private final CommonTestRepository repo;

    public FacultyExistsValidator(CommonTestRepository repo) {
        this.repo = repo;
    }

    @Override
    public ValidationResult<ValidationErrorFieldDto> validate(Object dto) {
        String faculty = null;
        Object value = FieldExtractor.findFieldByName(dto, ValidationContextKeys.FACULTY_EXISTS.getFieldName());

        if (value instanceof String s) {
            faculty = s;
        }

        ValidationResult<ValidationErrorFieldDto> result = ValidationResult.ok();
        if (faculty != null && !repo.facultyExistsByName(faculty)) {
            result.addError(new ValidationErrorFieldDto(
                    ValidationContextKeys.FACULTY_EXISTS.getFieldName(),
                    faculty,
                    ValidationMessages.NOT_FOUND_IN_DB)
            );
        }
        return result;
    }

    @Override
    public Set<ValidationContextKeys> getKeys() {
        return Set.of(ValidationContextKeys.FACULTY_EXISTS);
    }
}
