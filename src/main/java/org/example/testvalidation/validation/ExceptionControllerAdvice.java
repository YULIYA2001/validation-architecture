package org.example.testvalidation.validation;

import jakarta.validation.ValidationException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import org.example.testvalidation.exceptions.CsvProcessingException;
import org.example.testvalidation.exceptions.FailedAnnotationValidationException;
import org.example.testvalidation.exceptions.FailedBusinessValidationException;
import org.example.testvalidation.validation.error.dto.ValidationErrorMarker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// all needed custom errors here
@RestControllerAdvice
public class ExceptionControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(ExceptionControllerAdvice.class);


    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleException(IllegalStateException exp) {
        return ResponseEntity.badRequest().body(exp.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    /**
     * Обрабатывает кастомные исключения типа {@link FailedAnnotationValidationException}.
     *
     * @param ex возникшее исключение с ошибками валидации
     * @return ResponseEntity с кодом 400 и списком сообщений об ошибках
     */
    @ExceptionHandler(FailedAnnotationValidationException.class)
    public ResponseEntity<List<? extends ValidationErrorMarker>> handleException(FailedAnnotationValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getErrors());
    }

    /**
     * Обрабатывает кастомные исключения типа {@link FailedBusinessValidationException}.
     *
     * @param ex возникшее исключение с ошибками валидации
     * @return ResponseEntity с кодом 400 и списком сообщений об ошибках
     */
    @ExceptionHandler(FailedBusinessValidationException.class)
    public ResponseEntity<List<? extends ValidationErrorMarker>> handleException(FailedBusinessValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getErrors());
    }

    /**
     * Обрабатывает исключения {@link ValidationException}, возникающие при ошибках инициализации валидаторов,
     * например, если аннотация валидации неправильно сконфигурирована.
     *
     * <p>Если в исключении присутствует причина с сообщением, то оно логируется как ошибка (для разработчика).
     * Клиенту возвращается сообщение об общей внутренней ошибке валидации с HTTP-статусом 500.
     *
     * @param ex исключение {@link ValidationException}, перехваченное во время инициализации валидатора
     * @return объект {@link ResponseEntity} с HTTP статусом 500 и сообщением об ошибке
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof ValidationException && cause.getMessage() != null) {
            log.error("Ошибка инициализации валидатора: {}", cause.getMessage(), cause);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ошибка конфигурации валидации: " + ex.getMessage() + " Обратитесь к администратору.");
    }

    /**
     * Обрабатывает кастомные исключения типа {@link CsvProcessingException}.
     *
     * @param ex возникшее исключение при чтении csv в dto
     * @return ResponseEntity с кодом 400 и сообщением об ошибке
     */
    @ExceptionHandler(CsvProcessingException.class)
    public ResponseEntity<String> handleCsvProcessingException(CsvProcessingException ex) {
        log.error("Ошибка при обработке CSV: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка в формате CSV: " + ex.getMessage());
    }

    // так НЕ делать
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
