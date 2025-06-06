package org.example.testvalidation.validation.validators;

import org.example.testvalidation.repositories.CommonTestRepository;
import org.example.testvalidation.validation.core.api.ValidationContextKeys;
import org.example.testvalidation.validation.validators.base.FieldExistsValidator;
import org.springframework.stereotype.Component;

@Component
public class FacultyExistsValidator extends FieldExistsValidator<String> {
    public FacultyExistsValidator(CommonTestRepository repo) {
        super(
                ValidationContextKeys.FACULTY_EXISTS,
                String.class,
                repo::facultyExistsByName
        );
    }
}
