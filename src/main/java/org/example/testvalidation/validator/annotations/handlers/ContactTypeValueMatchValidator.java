package org.example.testvalidation.validator.annotations.handlers;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.regex.Pattern;
import org.example.testvalidation.dto.ContactDto;
import org.example.testvalidation.validator.annotations.ContactTypeValueMatch;
import org.example.testvalidation.validator.annotations.enums.ContactType;
import org.example.testvalidation.validator.utils.ValidationRegexps;

public class ContactTypeValueMatchValidator implements ConstraintValidator<ContactTypeValueMatch, ContactDto> {
    private static final Pattern MOBILE_PHONE_PATTERN = Pattern.compile(ValidationRegexps.ANY_MOBILE_PHONE_REGEXP);
    private static final Pattern EMAIL_PATTERN = Pattern.compile(ValidationRegexps.EMAIL_REGEXP);

    private String message;

    @Override
    public void initialize(ContactTypeValueMatch constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(ContactDto dto, ConstraintValidatorContext context) {
        if (dto == null || dto.getTypeId() == null || dto.getValue() == null) {
            return true;
        }

        String value = dto.getValue().trim();
        Long typeId = dto.getTypeId();

        boolean valid;
        if (Objects.equals(ContactType.MOBILE_PHONE.getId(), typeId)) {
            valid = MOBILE_PHONE_PATTERN.matcher(value).matches();
        } else if (Objects.equals(ContactType.EMAIL.getId(), typeId)) {
            valid = EMAIL_PATTERN.matcher(value).matches();
        } else {
            valid = true;
        }

        if (!valid) {
            buildConstraintViolation(
                    context,
                    message + String.format(" Значение поля %s для типа %s.", value, ContactType.getById(typeId))
                    );
        }

        return valid;
    }

    private void buildConstraintViolation(ConstraintValidatorContext context, String msg) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(msg).addPropertyNode("value").addConstraintViolation();
    }
}
