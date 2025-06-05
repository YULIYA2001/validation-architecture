package org.example.testvalidation.validation.validators;

import java.util.Set;
import org.example.testvalidation.repositories.CommonTestRepository;
import org.example.testvalidation.validation.core.api.ValidationResult;
import org.example.testvalidation.validation.error.dto.ValidationErrorFieldDto;
import org.example.testvalidation.validation.utils.FieldExtractor;
import org.example.testvalidation.validation.utils.ValidationMessages;
import org.springframework.stereotype.Component;

@Component
public class CitizenshipExistsValidator
        extends AbstractValidator<ValidationErrorFieldDto> {
    private final CommonTestRepository repo;

    public CitizenshipExistsValidator(CommonTestRepository repo) {
        this.repo = repo;
    }

    @Override
    public ValidationResult<ValidationErrorFieldDto> validate(Object dto) {
        Integer citizenship = null;
        Object value = FieldExtractor.findFieldByName(dto, "citizenship");

        if (value instanceof Integer i) {
            citizenship = i;
        }

        ValidationResult<ValidationErrorFieldDto> result = ValidationResult.ok();
        if (citizenship != null && !repo.citizenshipExistsByCode(citizenship)) {
            result.addError(new ValidationErrorFieldDto(
                    "citizenship",
                    citizenship,
                    ValidationMessages.NOT_FOUND_IN_DB)
            );
        }
        return result;
    }

    @Override
    public Set<String> getKeys() {
        return Set.of("checkCitizenship");
    }
}
