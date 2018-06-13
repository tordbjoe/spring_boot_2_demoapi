package com.example.demoapi.controller;

import com.example.demoapi.entity.ContactPerson;
import com.example.demoapi.repository.ContactPersonRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class CustomerController {

    private final ContactPersonRepository contactPersonRepository;

    public CustomerController(ContactPersonRepository contactPersonRepository) {
        this.contactPersonRepository = contactPersonRepository;
    }

    @GetMapping(value = "api/v1/contact", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Flux<ContactPerson> getAllContactPersons() {
        return contactPersonRepository.findAll();
    }

    @GetMapping(value = "api/v1/contact/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<ResponseEntity<ContactPerson>> getContactPersonById(@PathVariable(value = "id") String id) {
        return contactPersonRepository.findById(id)
                .map(savedContactPerson -> ResponseEntity.ok(savedContactPerson))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "api/v1/contact", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<ContactPerson> createContactPerson(@Valid @RequestBody ContactPerson contactPerson) {
        return contactPersonRepository.save(contactPerson);
    }
}
