package org.example.testvalidation.services;

import java.nio.file.AccessDeniedException;
import org.example.testvalidation.dto.StudentDto;
import org.example.testvalidation.validator.ObjectValidator;
import org.springframework.stereotype.Service;

@Service
public class StudentService extends EntityService {

    public StudentService(ObjectValidator validator) {
        super(validator);
    }

    public String uploadStudent(StudentDto studentDto) throws AccessDeniedException {
        this.isAccessGranted();
        this.validateByAnnotations(studentDto);
        // map dto to entity, save entity
        return this.save(studentDto);
    }
}
