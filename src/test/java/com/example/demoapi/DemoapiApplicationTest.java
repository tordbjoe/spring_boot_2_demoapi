package com.example.demoapi;


import com.example.demoapi.entity.ContactPerson;
import com.example.demoapi.repository.ContactPersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class DemoapiApplicationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ContactPersonRepository contactPersonRepository;

    @Before
    public void setUp() {
        contactPersonRepository.deleteAll().block();
    }

    @Test
    public void testGetContactPerson__isOK() {

        ContactPerson newContactPerson = ContactPerson.builder()
                .name("Test Person")
                .phone("99999999")
                .company("Test Company")
                .email("test.person@testcompany.com")
                .position("QA")
                .department("QA")
                .build();

        ContactPerson savedContactPerson = contactPersonRepository.save(newContactPerson).block();

        webTestClient.get().uri("/api/v2/contact/" + savedContactPerson.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(ContactPerson.class);

    }

    @Test
    public void testGetAllContactPersons__isOK() {

        Flux<ContactPerson> contactPersons = Flux.just(
                ContactPerson.builder()
                        .name("Stellan Skjærsgård")
                        .phone("99991111")
                        .company("Stellan Industries")
                        .department("Strategy")
                        .email("stellan.skjaersgard@stellan.se")
                        .position("CEO")
                        .build(),
                ContactPerson.builder()
                        .name("Simon Salesperson")
                        .phone("99343434")
                        .company("Stellan Industries")
                        .department("Sales")
                        .email("simon.salespersonr@stellan.se")
                        .position("Sales Consultant")
                        .build()
        );

        StepVerifier.create(contactPersonRepository.saveAll(contactPersons)).expectNextCount(2).verifyComplete();

        webTestClient.get().uri("/api/v2/contact")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(ContactPerson.class)
                .hasSize(2);

    }

    @Test
    public void testCreateContactPerson__isOK() {

        ContactPerson newContactPerson = ContactPerson.builder()
                .name("Test Person")
                .phone("99999999")
                .company("Test Company")
                .email("test.person@testcompany.com")
                .position("QA")
                .department("QA")
                .build();

        webTestClient.post().uri("/api/v2/contact")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(newContactPerson), ContactPerson.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.name").isEqualTo(newContactPerson.getName());
    }

}
