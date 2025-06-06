package org.example.testvalidation.validation.validators.base;

import java.util.Set;
import java.util.function.Predicate;
import org.example.testvalidation.validation.core.api.ValidationContextKeys;
import org.example.testvalidation.validation.core.api.ValidationResult;
import org.example.testvalidation.validation.error.dto.ValidationErrorFieldDto;
import org.example.testvalidation.validation.utils.FieldExtractor;
import org.example.testvalidation.validation.utils.ValidationMessages;

public class FieldExistsValidator<T> extends AbstractValidator<ValidationErrorFieldDto> {
    private final ValidationContextKeys key;
    private final Class<T> valueType;
    private final Predicate<T> existenceCheck;

    public FieldExistsValidator(ValidationContextKeys key,
                                Class<T> valueType,
                                Predicate<T> existenceCheck) {
        this.key = key;
        this.valueType = valueType;
        this.existenceCheck = existenceCheck;
    }

    @Override
    public ValidationResult<ValidationErrorFieldDto> validate(Object dto) {
        Object rawValue = FieldExtractor.findFieldByName(dto, key.getFieldName());

        ValidationResult<ValidationErrorFieldDto> result = ValidationResult.ok();

        if (valueType.isInstance(rawValue)) {
            T value = valueType.cast(rawValue);
            if (value != null && !existenceCheck.test(value)) {
                result.addError(new ValidationErrorFieldDto(
                        key.getFieldName(),
                        value,
                        ValidationMessages.NOT_FOUND_IN_DB
                ));
            }
        }

        return result;
    }

    @Override
    public Set<ValidationContextKeys> getKeys() {
        return Set.of(key);
    }
}