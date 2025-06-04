package org.example.testvalidation.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;
import org.example.testvalidation.validation.annotations.handlers.ContactTypeValueMatchValidator;
import org.example.testvalidation.validation.utils.ValidationMessages;

/**
 * Аннотация для валидации контактов.
 * <p>
 * Применяется специфично к классу ContactDto
 * <p>
 * Валидатор {@link ContactTypeValueMatchValidator} проверяет соответствие значения поля {@code value}
 * заданному шаблону в зависимости от типа контакта. Проверяются по шаблонам только email
 * и мобильный телефон (общий шаблон, не специализирован под РБ).
 */
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ContactTypeValueMatchValidator.class)
public @interface ContactTypeValueMatch {
    String message() default ValidationMessages.FORMAT_MISMATCH;

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
