package org.example.testvalidation.services;

import java.nio.file.AccessDeniedException;
import org.example.testvalidation.dto.StudentDto;
import org.example.testvalidation.validation.AnnotationObjectValidator;
import org.example.testvalidation.validation.BusinessObjectValidator;
import org.example.testvalidation.validation.core.api.ValidationContext;
import org.example.testvalidation.validation.core.api.ValidationContextKeys;
import org.springframework.stereotype.Service;

@Service
public class StudentService extends EntityService {

    public StudentService(AnnotationObjectValidator annotationValidator, BusinessObjectValidator businessValidator) {
        super(annotationValidator, businessValidator);
    }

    /*
     * Процесс валидации проходит так:
     * - Валидация аннотаций -> проброс исключения или идем дальше
     * - Валидация бизнес-логики -> проброс исключения или идем дальше
     * - Сохранение
     */
    public String uploadStudent(StudentDto studentDto) throws AccessDeniedException {
        this.checkAccessGranted();
        this.validateByAnnotations(studentDto);
        this.validateBusinessLogic(
                studentDto,
                ValidationContext.of(
                        ValidationContextKeys.CITIZENSHIP_EXISTS,
                        ValidationContextKeys.FACULTY_EXISTS,
                        ValidationContextKeys.STUDENT
                )
        );
        return this.save(studentDto);
    }
}
