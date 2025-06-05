package org.example.testvalidation.validation.validators;

import org.example.testvalidation.dto.PersonDto;
import org.example.testvalidation.repositories.CitizenshipRepository;
import org.example.testvalidation.validation.core.ValidationResult;
import org.example.testvalidation.validation.error.dto.ValidationErrorFieldDto;
import org.springframework.stereotype.Component;

@Component
public class CitizenshipExistsValidator extends AbstractValidator<PersonDto> {
    private final CitizenshipRepository repo;

    public CitizenshipExistsValidator(CitizenshipRepository repo) {
        super(PersonDto.class);
        this.repo = repo;
    }

    @Override
    public ValidationResult<ValidationErrorFieldDto> validate(PersonDto dto) {
        ValidationResult<ValidationErrorFieldDto> result = ValidationResult.ok();
        if (!repo.existsByCode(dto.getIdentityCard().getCitizenship())) {
            result.addError(new ValidationErrorFieldDto(
                    "identityCard.citizenship",
                    "БЛ",
                    "Гражданство не найдено")
            );
        }
        return result;
    }
}
