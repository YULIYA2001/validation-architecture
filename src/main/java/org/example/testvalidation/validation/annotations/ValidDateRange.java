package org.example.testvalidation.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;
import org.example.testvalidation.validation.annotations.enums.DateComparisonMode;
import org.example.testvalidation.validation.annotations.handlers.ValidDateRangeValidator;

/**
 * Аннотация для проверки корректности диапазона дат в строковом формате {@code dd.MM.yyyy}
 * <p>
 *     Использование возможно на уровне класса — для сравнения двух полей или для сравнения одного поля с текущей датой.
 * <p>
 *     Поведение определяется комбинацией параметров {@code dateBeforeName} и {@code dateAfterName}:
 * <ul>
 *   <li>Если указаны оба — сравниваются два поля: {@code dateBeforeName <= dateAfterName}</li>
 *   <li>Если указан только {@code dateBeforeName} — сравнивается значение поля с текущей датой: {@code field <= now}</li>
 *   <li>Если указан только {@code dateAfterName} — сравнивается значение поля с текущей датой: {@code now <= field}</li>
 * </ul>
 *
 * Параметр {@code strictness} определяет уровень строгости сравнения (по умолчанию — нестрогое):
 * <ul>
 *   <li>{@code INCLUSIVE} — допускает равенство дат</li>
 *   <li>{@code EXCLUSIVE} — требует строгое соблюдение порядка</li>
 * </ul>
 *
 * <p>
 *     Формат даты должен соответствовать шаблону {@code dd.MM.yyyy}. В противном случае произойдёт ошибка валидации.
 *     При использовании данной аннотации проверять формат полей, их наличие и пустоту дополнительно не требуется,
 *     т.е. @Pattern, @NotBlank на поле дополнительно не нужны (при их указании в случае ошибки валидации просто
 *     продублируется сообщение об ошибке)
 */
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ValidDateRange.List.class)
@Constraint(validatedBy = ValidDateRangeValidator.class)
public @interface ValidDateRange {
    String message() default "";
    String dateBeforeName() default "";
    String dateAfterName() default "";
    DateComparisonMode strictness() default DateComparisonMode.INCLUSIVE;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        ValidDateRange[] value();
    }
}
