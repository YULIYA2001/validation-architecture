package org.example.testvalidation.validation.core.api;

import java.util.Set;

public interface ConditionalValidator {
    Set<ValidationContextKeys> getKeys(); // Уникальные ключи (напр. "checkCitizenship")
}