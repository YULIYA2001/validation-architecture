package org.example.testvalidation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.example.testvalidation.validator.messages.ValidationMessages;

public class EmployeeDto {
    @Valid
    @NotNull(message = ValidationMessages.EMPTY_FIELD)
    public PersonDto person;
}
