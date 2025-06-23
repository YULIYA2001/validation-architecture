package org.example.testvalidation.validation.core.api;

/**
 * Перечисление имен полей, которые нужны для валидации (используются для рекурсивного поиска в DTO)
 */
public enum ValidationFields {
    NO_FIELD(""),
    CITIZENSHIP("citizenship"),
    FACULTY("faculty"),
    LAST_NAME("lastName"),
    FIRST_NAME("firstName"),
    GROUP_NUMBER("groupNumber");

    private final String name;

    ValidationFields(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
