package org.example.testvalidation.services;

import java.nio.file.AccessDeniedException;
import org.example.testvalidation.dto.StudentDto;
import org.example.testvalidation.validation.AnnotationObjectValidator;
import org.example.testvalidation.validation.BusinessObjectValidator;
import org.springframework.stereotype.Service;

@Service
public class StudentService extends EntityService {

    public StudentService(AnnotationObjectValidator annotationValidator, BusinessObjectValidator businessValidator) {
        super(annotationValidator, businessValidator);
    }

    // В данном случае настройка следующая:
    // если не прошла валидация аннотаций, валидация бизнес-логики не пойдет (проброс исключения);
    // если не прошла валидация бизнес-логики, сохранение не пойдет (проброс исключения);
    // если прошли обе валидации, начинается сохранение;
    // причина: смысл логической валидации, если поле пустое.
    // (если валидации делать две сразу, то во второй нужны доп. проверки на null, blank)
    public String uploadStudent(StudentDto studentDto) throws AccessDeniedException {
        this.checkAccessGranted();
        this.validateByAnnotations(studentDto);
        this.validateBusinessLogic(studentDto);
        // map dto to entity, save entity
        return this.save(studentDto);
    }
}
