package com.example.demoapi.handler;

import com.example.demoapi.entity.ContactPerson;
import com.example.demoapi.repository.ContactPersonRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class ContactPersonHandler {

    private final ContactPersonRepository contactPersonRepository;

    public ContactPersonHandler(ContactPersonRepository contactPersonRepository) {
        this.contactPersonRepository = contactPersonRepository;
    }

    public Mono<ServerResponse> getAllContactPersons(ServerRequest serverRequest) {
        return ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(contactPersonRepository.findAll(), ContactPerson.class);
    }

    public Mono<ServerResponse> getContactPersonById(ServerRequest serverRequest) {
        final String id = serverRequest.pathVariable("id");
        final Mono<ContactPerson> contactPerson = contactPersonRepository.findById(id);
        return contactPerson.flatMap(c ->
                    ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(fromPublisher(contactPerson, ContactPerson.class)))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> createContactPerson(ServerRequest serverRequest) {
        Mono<ContactPerson> newCustomer = serverRequest.body(toMono(ContactPerson.class));
        final Mono<ContactPerson> created = newCustomer.flatMap(customer -> contactPersonRepository.save(customer));
        return ok().contentType(MediaType.APPLICATION_JSON_UTF8 ).body(created, ContactPerson.class);
    }
}
