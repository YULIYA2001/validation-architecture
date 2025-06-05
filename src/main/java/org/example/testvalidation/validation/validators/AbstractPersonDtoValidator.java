package org.example.testvalidation.validation.validators;

import org.example.testvalidation.dto.PersonDto;

public abstract class AbstractPersonDtoValidator<T extends PersonDto> extends AbstractValidator<T> {
    protected AbstractPersonDtoValidator(Class<T> type) {
        super(type);
    }
}
