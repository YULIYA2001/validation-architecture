package org.example.testvalidation.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.*;
import java.util.stream.Collectors;
import org.example.testvalidation.exceptions.FailedAnnotationValidationException;
import org.springframework.stereotype.Component;

// !!! формат возвращаемых сообщений "от балды" (не согласован)
@Component
public class ObjectValidator {
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();

    /**
     * Валидирует на основании аннотаций объект
     *
     * @param objectToValidate Объект обобщенного типа для валидации
     * @throws FailedAnnotationValidationException если валидация не пройдена
     */
    public <T> void validate(T objectToValidate) {
        // TODO OrderedChecks.class нужен, чтобы сообщения об ошибках проверки не шли вперемешку
        Set<ConstraintViolation<T>> violations = validator.validate(objectToValidate);
        if (!violations.isEmpty()) {
            var errorMessages = violations.stream()
                    .map(violation -> String.format(
                            "Поле: %s. Значение: %s. Ошибка: %s",
                            getFieldPath(violation),
                            getInvalidValue(violation),
                            violation.getMessage())
                    )
                    .collect(Collectors.toSet());
            throw new FailedAnnotationValidationException(errorMessages);
        }
    }

    /**
     * Возвращает строковое представление пути к полю, на которое ссылается нарушение ограничения.
     * <p>
     * Если аннотация расположена на уровне класса и {@code getPropertyPath()} возвращает пустую строку,
     * то вместо этого возвращается простое имя класса объекта.
     *
     * @param violation нарушение валидации
     * @return путь к полю или имя класса, если нарушение уровня класса
     */
    private <T> String getFieldPath(ConstraintViolation<T> violation) {
        String path = violation.getPropertyPath().toString();
        if (path.isBlank()) {
            return violation.getRootBeanClass().getSimpleName();
        }
        return path;
    }

    /**
     * Возвращает значение, вызвавшее нарушение валидации.
     * <p>
     * Для простых типов возвращается строковое представление значения.
     * Для сложных объектов (например, DTO) возвращается строка {@code "<объект>"},
     * чтобы избежать вывода всей структуры объекта.
     *
     * @param violation нарушение валидации
     * @return строковое представление недопустимого значения или {@code "<объект>"} для составных типов
     */
    private <T> String getInvalidValue(ConstraintViolation<T> violation) {
        Object invalidValue = violation.getInvalidValue();
        boolean isComplexObject = invalidValue != null
                && !(invalidValue instanceof CharSequence
                || invalidValue instanceof Number
                || invalidValue instanceof Boolean
                || invalidValue.getClass().isPrimitive());
        return isComplexObject ? "<объект>" : String.valueOf(invalidValue);
    }

    /**
     * Валидирует на основании аннотаций список объектов
     * Это перегрузка метода validate() для обработки массива
     *
     * @param objectsToValidate Список объектов обобщенного типа для валидации
     * @throws FailedAnnotationValidationException если валидация не пройдена;
     *         в качестве сообщения об ошибке содержит набор ошибок от каждого
     *         объекта списка, предваренный его индексом, в порядке элементов в массиве
     */
    // возможно, понятнее будет писать букву колонки вместо названия поля или и то, и другое
    // или создать мапу с соответствующими русскими названиями
    public <T> void validate(List<T> objectsToValidate) {
        List<String> errorMessages = new ArrayList<>();
        int index = 0;
        for (T object : objectsToValidate) {
            try {
                validate(object);
            } catch (FailedAnnotationValidationException e) {
                errorMessages.add(String.format(
                        "%n Строка %d: %n%s",
                        index,
                        String.join("\n", e.getErrorMessages())
                ));
            }
            index++;
        }
        if (!errorMessages.isEmpty()) {
            throw new FailedAnnotationValidationException(new LinkedHashSet<>(errorMessages));
        }
    }

}
