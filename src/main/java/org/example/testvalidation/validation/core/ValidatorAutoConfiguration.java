package org.example.testvalidation.validation.core;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorAutoConfiguration {
    @Autowired
    public void configureValidators(List<BusinessValidator<?>> validators, ValidatorRegistry registry) {
        for (BusinessValidator<?> validator : validators) {
            registry.register(validator);
        }
    }
}
