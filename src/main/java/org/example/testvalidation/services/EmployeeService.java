package org.example.testvalidation.services;

import org.example.testvalidation.dto.EmployeeDto;
import org.example.testvalidation.validator.ObjectValidator;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService extends EntityService {

    public EmployeeService(ObjectValidator validator) {
        super(validator);
    }

    public String uploadEmployee(EmployeeDto employeeDto) {
        this.validateByAnnotations(employeeDto);
        // map dto to entity, save entity
        return this.save(employeeDto);
    }
}
