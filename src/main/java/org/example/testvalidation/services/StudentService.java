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

    /**
     * В данном примере процесс валидации проходит так:
     *
     * <ul>
     *     <li> валидация аннотаций -> проброс исключения или идем дальше </li>
     *     <li> валидация бизнес-логики -> проброс исключения или идем дальше </li>
     *     <li> сохранение </li>
     * </ul>
     *
     * <p> Причина: мало смысла в логической валидации, если не прошли простые проверки (пуста, паттерн, ...)
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
        // map dto to entity, save entity
        return this.save(studentDto);
    }
}
