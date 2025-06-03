package org.example.testvalidation.controllers;

import java.nio.file.AccessDeniedException;
import org.example.testvalidation.dto.StudentDto;
import org.example.testvalidation.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<String> uploadStudent(@RequestBody StudentDto studentDto) throws AccessDeniedException {
        final String saveMsg = studentService.uploadStudent(studentDto);
        return ResponseEntity.ok().body(saveMsg);
    }
}
