package org.example.testvalidation.validation.validators;

import org.example.testvalidation.repositories.CommonTestRepository;
import org.example.testvalidation.validation.core.api.ValidationContextKeys;
import org.example.testvalidation.validation.validators.base.FieldExistsConditionalValidator;
import org.springframework.stereotype.Component;

@Component
public class CitizenshipExistsConditionalValidator extends FieldExistsConditionalValidator<Integer> {
    public CitizenshipExistsConditionalValidator(CommonTestRepository repo) {
        super(
                ValidationContextKeys.CITIZENSHIP_EXISTS,
                Integer.class,
                repo::citizenshipExistsByCode
        );
    }
}
