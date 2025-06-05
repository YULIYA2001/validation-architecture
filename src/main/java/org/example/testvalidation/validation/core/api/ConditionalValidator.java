package org.example.testvalidation.validation.core.api;

import java.util.Set;

public interface ConditionalValidator {
    Set<String> getKeys(); // Уникальные ключи (напр. "checkCitizenship")
}