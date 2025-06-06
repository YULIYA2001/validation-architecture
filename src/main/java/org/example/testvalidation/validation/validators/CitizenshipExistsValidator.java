package org.example.testvalidation.validation.validators;

import org.example.testvalidation.repositories.CommonTestRepository;
import org.example.testvalidation.validation.core.api.ValidationContextKeys;
import org.example.testvalidation.validation.validators.base.FieldExistsValidator;
import org.springframework.stereotype.Component;

@Component
public class CitizenshipExistsValidator extends FieldExistsValidator<Integer> {
    public CitizenshipExistsValidator(CommonTestRepository repo) {
        super(
                ValidationContextKeys.CITIZENSHIP_EXISTS,
                Integer.class,
                repo::citizenshipExistsByCode
        );
    }
}
