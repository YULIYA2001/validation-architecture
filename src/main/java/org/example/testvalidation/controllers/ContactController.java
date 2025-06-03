package org.example.testvalidation.controllers;

import java.io.IOException;
import java.util.List;
import org.example.testvalidation.dto.ContactDto;
import org.example.testvalidation.services.ContactService;
import org.example.testvalidation.utils.CsvUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/contact")
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }


    @PostMapping("/uploadCsv")
    public ResponseEntity<String> uploadContactsCsvFile(@RequestParam("file") MultipartFile file) throws IOException {
        List<ContactDto> dtos = CsvUtils.readCsv(file.getInputStream(), ContactDto.class, ';', false);
        final String saved = contactService.uploadContacts(dtos);
        return ResponseEntity.ok().body(saved);
    }
}
