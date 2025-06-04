package org.example.testvalidation.services;

import java.nio.file.AccessDeniedException;
import java.util.List;
import org.example.testvalidation.dto.ContactDto;
import org.example.testvalidation.validator.ObjectValidator;
import org.springframework.stereotype.Service;

@Service
public class ContactService extends EntityService {

    public ContactService(ObjectValidator validator) {
        super(validator);
    }

    public String uploadContacts(List<ContactDto> contactDtos) throws AccessDeniedException {
        this.checkAccessGranted();
        this.validateByAnnotations(contactDtos);
        // map dto to entity, save entity
        return this.save(contactDtos);
    }
}

