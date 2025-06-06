package org.example.testvalidation.exceptions;

/**
 * Исключение, выбрасываемое при ошибке обработки CSV-файлов
 */
public class CsvProcessingException extends RuntimeException {
    public CsvProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
