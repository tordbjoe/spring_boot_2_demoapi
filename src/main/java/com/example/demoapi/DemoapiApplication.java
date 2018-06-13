package com.example.demoapi;

import com.example.demoapi.entity.ContactPerson;
import com.example.demoapi.repository.ContactPersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@EnableReactiveMongoRepositories
@SpringBootApplication
public class DemoapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoapiApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ContactPersonRepository contactPersonRepository) {
        return args -> {

            Flux<ContactPerson> contactPersons = Flux.just(
                    ContactPerson.builder().name("Harold Android").phone("99343434").company("Google").department("Sales").email("harold.android@google.com").position("Manager").build(),
                    ContactPerson.builder().name("Lisa Apple").phone("99343434").company("Apple").department("Sales").email("lisa.apple@apple.com").position("Sales Consultant").build()
            )
                    .flatMap(customer -> contactPersonRepository.save(customer));

            contactPersons.thenMany(contactPersonRepository.findAll()).publishOn(Schedulers.single()).subscribe(System.out::println);
        };
    }
}
