package org.example.testvalidation.controllers;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.IOException;
import java.util.List;
import org.example.testvalidation.dto.ContactDto;
import org.example.testvalidation.services.ContactService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> uploadContactsCsvFile(@RequestParam("file") MultipartFile file) {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(ContactDto.class).withColumnSeparator(';').withUseHeader(false);

        ObjectReader reader = mapper.readerFor(ContactDto.class).with(schema);

        MappingIterator<ContactDto> iterator;
        try {
            iterator = reader.readValues(file.getInputStream());
            List<ContactDto> dtos = iterator.readAll();
            final String saved = contactService.uploadContacts(dtos);
            return ResponseEntity.ok().body(saved);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
