package org.example.testvalidation.validation.error.dto;

import org.example.testvalidation.exceptions.FailedAnnotationValidationException;

/**
 * Маркерный интерфейс для DTO, описывающих ошибки валидации.
 * <p>
 * Используется в {@link FailedAnnotationValidationException} для поддержки обобщённой обработки различных
 * форматов ошибок.
 * </p>
 */
public interface ValidationErrorMarker {
}
