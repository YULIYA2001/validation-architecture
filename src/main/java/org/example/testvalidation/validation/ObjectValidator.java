package org.example.testvalidation.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.*;
import org.example.testvalidation.exceptions.FailedAnnotationValidationException;
import org.example.testvalidation.validation.error.dto.ValidationErrorFieldDto;
import org.example.testvalidation.validation.error.dto.ValidationErrorRowDto;
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
                    .map(violation -> new ValidationErrorFieldDto(
                            getFieldPath(violation),
                            getInvalidValue(violation),
                            violation.getMessage()
                    ))
                    .toList();
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
     * @throws FailedAnnotationValidationException если валидация не пройдена; в качестве сообщения об ошибке
     *                                             содержит набор ошибок от каждого объекта списка, предваренный
     *                                             его индексом, в порядке элементов в массиве
     */
    // возможно, понятнее будет писать букву колонки вместо названия поля или и то, и другое
    // или создать мапу с соответствующими русскими названиями
    public <T> void validate(List<T> objectsToValidate) {
        List<ValidationErrorRowDto> errorRowDtos = new ArrayList<>();
        for (int index = 0; index < objectsToValidate.size(); index++) {
            T object = objectsToValidate.get(index);
            try {
                validate(object);
            } catch (FailedAnnotationValidationException e) {
                List<ValidationErrorFieldDto> fieldErrors = e.getErrors().stream()
                        .map(error -> {
                            if (!(error instanceof ValidationErrorFieldDto dto)) {
                                throw new IllegalStateException("Unexpected error type: " + error.getClass());
                            }
                            return dto;
                        })
                        .toList();
                errorRowDtos.add(new ValidationErrorRowDto(index, fieldErrors));
            }
        }
        if (!errorRowDtos.isEmpty()) {
            throw new FailedAnnotationValidationException(new ArrayList<>(errorRowDtos));
        }
    }

}
