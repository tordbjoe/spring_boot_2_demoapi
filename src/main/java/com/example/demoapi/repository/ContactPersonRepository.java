package com.example.demoapi.repository;

import com.example.demoapi.entity.ContactPerson;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ContactPersonRepository extends ReactiveMongoRepository<ContactPerson, String> {

    Mono<ContactPerson> findById(String id);

}
