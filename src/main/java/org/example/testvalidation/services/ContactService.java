package org.example.testvalidation.services;

import java.util.List;
import org.example.testvalidation.dto.ContactDto;
import org.example.testvalidation.validator.ObjectValidator;
import org.springframework.stereotype.Service;

@Service
public class ContactService extends EntityService {

    public ContactService(ObjectValidator validator) {
        super(validator);
    }

    public String uploadContacts(List<ContactDto> contactDtos) {
        this.validateByAnnotations(contactDtos);
        // map dto to entity, save entity
        return this.save(contactDtos);
    }
}

