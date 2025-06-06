package org.example.testvalidation.validation.validators;

import org.example.testvalidation.repositories.CommonTestRepository;
import org.example.testvalidation.validation.core.api.ValidationContextKeys;
import org.example.testvalidation.validation.validators.base.FieldExistsConditionalValidator;
import org.springframework.stereotype.Component;

@Component
public class FacultyExistsConditionalValidator extends FieldExistsConditionalValidator<String> {
    public FacultyExistsConditionalValidator(CommonTestRepository repo) {
        super(
                ValidationContextKeys.FACULTY_EXISTS,
                String.class,
                repo::facultyExistsByName
        );
    }
}
