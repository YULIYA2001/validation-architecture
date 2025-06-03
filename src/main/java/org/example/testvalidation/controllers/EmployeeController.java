package org.example.testvalidation.controllers;

import org.example.testvalidation.dto.EmployeeDto;
import org.example.testvalidation.services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<String> uploadEmployee(@RequestBody EmployeeDto employeeDto) {
        final String saveMsg = employeeService.uploadEmployee(employeeDto);
        return ResponseEntity.ok().body(saveMsg);
    }
}
