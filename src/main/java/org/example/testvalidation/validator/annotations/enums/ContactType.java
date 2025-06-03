package org.example.testvalidation.validator.annotations.enums;

import java.util.Arrays;

public enum ContactType {
    MOBILE_PHONE(1L),
    EMAIL(2L);

    private final Long id;

    ContactType(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    // Плохой пример. Так нельзя делать
    public static ContactType getById(Long id) {
        return Arrays.stream(values())
                .filter(type -> type.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
