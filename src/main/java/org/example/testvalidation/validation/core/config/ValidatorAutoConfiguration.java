package org.example.testvalidation.validation.core.config;

import java.util.List;
import org.example.testvalidation.validation.core.api.BusinessValidator;
import org.example.testvalidation.validation.core.ValidatorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация для автоматической регистрации валидаторов
 */
@Configuration
public class ValidatorAutoConfiguration {
    @Autowired
    public void configureValidators(List<BusinessValidator<?>> validators, ValidatorRegistry registry) {
        for (BusinessValidator<?> validator : validators) {
            registry.register(validator);
        }
    }
}
