package org.example.testvalidation.validation.validators;

import org.example.testvalidation.dto.IdentityCardDto;
import org.example.testvalidation.dto.PersonDto;
import org.example.testvalidation.repositories.CitizenshipRepository;
import org.example.testvalidation.validation.core.ValidationResult;
import org.example.testvalidation.validation.error.dto.ValidationErrorFieldDto;
import org.example.testvalidation.validation.utils.ValidationMessages;
import org.springframework.stereotype.Component;

@Component
public class CitizenshipExistsValidator extends AbstractValidator<ValidationErrorFieldDto> {
    private final CitizenshipRepository repo;

    public CitizenshipExistsValidator(CitizenshipRepository repo) {
        this.repo = repo;
    }

    @Override
    public ValidationResult<ValidationErrorFieldDto> validate(Object dto) {
        Integer citizenship = null;
        if (dto instanceof PersonDto person && person.getIdentityCard() != null) {
            citizenship = person.getIdentityCard().getCitizenship();
        } else if (dto instanceof IdentityCardDto card) {
            citizenship = card.getCitizenship();
        }

        ValidationResult<ValidationErrorFieldDto> result = ValidationResult.ok();
        if (citizenship != null && !repo.existsByCode(citizenship)) {
            result.addError(new ValidationErrorFieldDto(
                    "citizenship",
                    citizenship,
                    ValidationMessages.NOT_FOUND_IN_DB)
            );
        }
        return result;
    }

    @Override
    public boolean supports(Object dto) {
        if (dto instanceof PersonDto person) {
            return person.getIdentityCard() != null;
        }
        if (dto instanceof IdentityCardDto) {
            return true;
        }
        return false;
    }
}
