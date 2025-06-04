package org.example.testvalidation.services;

import java.nio.file.AccessDeniedException;
import org.example.testvalidation.dto.StudentDto;
import org.example.testvalidation.validation.ObjectValidator;
import org.springframework.stereotype.Service;

@Service
public class StudentService extends EntityService {

    public StudentService(ObjectValidator validator) {
        super(validator);
    }

    public String uploadStudent(StudentDto studentDto) throws AccessDeniedException {
        this.checkAccessGranted();
        this.validateByAnnotations(studentDto);
        // map dto to entity, save entity
        return this.save(studentDto);
    }
}
