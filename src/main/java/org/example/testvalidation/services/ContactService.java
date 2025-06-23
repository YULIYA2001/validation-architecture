package org.example.testvalidation.services;

import java.nio.file.AccessDeniedException;
import java.util.List;
import org.example.testvalidation.dto.ContactDto;
import org.example.testvalidation.validation.AnnotationObjectValidator;
import org.example.testvalidation.validation.BusinessObjectValidator;
import org.springframework.stereotype.Service;

@Service
public class ContactService extends EntityService {

    public ContactService(AnnotationObjectValidator annotationValidator, BusinessObjectValidator businessValidator) {
        super(annotationValidator, businessValidator);
    }

    public String uploadContacts(List<ContactDto> dtoList) throws AccessDeniedException {
        this.checkAccessGranted();
        this.validateByAnnotations(dtoList);
        return this.save(dtoList);
    }
}

