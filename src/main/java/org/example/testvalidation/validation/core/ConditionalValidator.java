package org.example.testvalidation.validation.core;

import java.util.Set;

public interface ConditionalValidator {
    Set<String> getKeys(); // Уникальные ключи (напр. "checkCitizenship")
}