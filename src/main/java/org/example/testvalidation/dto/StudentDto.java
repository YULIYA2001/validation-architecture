package org.example.testvalidation.dto;

import jakarta.validation.constraints.*;
import org.example.testvalidation.validation.utils.ValidationMessages;
import org.example.testvalidation.validation.utils.ValidationRegexps;
import org.hibernate.validator.constraints.Range;

public class StudentDto extends PersonDto {
    @NotBlank(message = ValidationMessages.EMPTY_FIELD)
    @Pattern(regexp = ValidationRegexps.GROUP_NUMBER_REGEXP, message = ValidationMessages.FORMAT_MISMATCH_WITH_REGEXP)
    private String groupNumber;

    @NotNull(message = ValidationMessages.EMPTY_FIELD)
    @Range(min = 0, max=100, message = ValidationMessages.WRONG_VALUE_FROM_RANGE)
    private Integer educationType;

    @NotBlank(message = ValidationMessages.EMPTY_FIELD)
    @Pattern(regexp = ValidationRegexps.RUSSIAN_LETTERS_REGEXP, message = ValidationMessages.FORMAT_MISMATCH_WITH_REGEXP)
    private String faculty;

    public String getGroupNumber() {
        return groupNumber;
    }

    public Integer getEducationType() {
        return educationType;
    }

    public String getFaculty() {
        return faculty;
    }

    @Override
    public String toString() {
        return "StudentDto{" +
                "groupNumber='" + groupNumber + '\'' +
                ", educationType=" + educationType +
                ", faculty='" + faculty + '\'' +
                "} " + super.toString();
    }
}
