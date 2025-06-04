package org.example.testvalidation.validator.annotations.handlers;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.example.testvalidation.validator.annotations.ValidDateRange;
import org.example.testvalidation.validator.annotations.enums.DateComparisonMode;
import org.example.testvalidation.validator.utils.ValidationMessages;

@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
public class ValidDateRangeValidator implements ConstraintValidator<ValidDateRange, Object> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final String EMPTY_MSG = "";

    private String before;
    private String after;
    private DateComparisonMode strictness;
    private String message;

    @Override
    public void initialize(ValidDateRange constraintAnnotation) {
        this.before = constraintAnnotation.dateBeforeName();
        this.after = constraintAnnotation.dateAfterName();
        this.message = constraintAnnotation.message();
        this.strictness = constraintAnnotation.strictness();

        if ((before == null || before.isBlank()) && (after == null || after.isBlank())) {
            throw new ValidationException("@ValidDateRange требует указания хотя бы одного поля: dateBeforeName или dateAfterName");
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            LocalDate date1 = stringToDate(value, before);
            LocalDate date2 = stringToDate(value, after);
            LocalDate now = LocalDate.now();

            if (date1 != null && date2 != null) {
                return compareDates(date1, date2, context, String.format(" %s должна быть %s %s.", before, strictness.getAfterMsg(), after));
            } else if (date1 != null) {
                return compareDates(date1, now, context, String.format(" %s должна быть %s текущей даты.", before, strictness.getBeforeMsg()));
            } else if (date2 != null) {
                return compareDates(now, date2, context, String.format(" %s должна быть %s текущей даты.", after, strictness.getAfterMsg()));
            }
            return true;
        } catch (DateTimeParseException e) {
            buildConstraintViolation(
                    context,
                    String.format(
                            "Одно или несколько полей неверного формата: %s, %s. %s",
                            before,
                            after,
                            ValidationMessages.DATE_FORMAT_MISMATCH
                    )
            );
            return false;
        }
    }

    private LocalDate stringToDate(Object value, String fieldName) throws ValidationException, DateTimeParseException {
        if (value == null || fieldName == null || fieldName.isBlank()) return null;

        try {
            Field field = findFieldRecursively(value.getClass(), fieldName);
            Object raw = field.get(value);
            if (raw instanceof String str && !str.isBlank()) {
                return LocalDate.parse(str, formatter);
            }
        } catch (IllegalAccessException e) {
            throw new ValidationException("Ошибка при извлечении поля '" + fieldName + "': " + e.getMessage());
        }

        return null;
    }

    private Field findFieldRecursively(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new IllegalArgumentException("Поле '" + fieldName + "' не найдено в иерархии класса.");
    }

    private boolean compareDates(LocalDate date1,
                                 LocalDate date2,
                                 ConstraintValidatorContext context,
                                 String defaultMsg) {
        boolean result = switch (strictness) {
            case INCLUSIVE -> !date1.isAfter(date2);
            case EXCLUSIVE -> date1.isBefore(date2);
        };

        if (result) {
            return true;
        } else {
            buildConstraintViolation(context, getExactMessage(defaultMsg));
            return false;
        }
    }

    private String getExactMessage(String defaultMsg) {
        if (EMPTY_MSG.equals(message)) {
            return ValidationMessages.WRONG_DATE_RANGE + defaultMsg;
        }
        return message;
    }

    private void buildConstraintViolation(ConstraintValidatorContext context, String msg) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
    }
}
