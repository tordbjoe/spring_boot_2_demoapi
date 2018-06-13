package com.example.demoapi.router;

import com.example.demoapi.handler.ContactPersonHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ContactPersonRouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> customerRouterFunction(ContactPersonHandler contactPersonHandler) {

        return route(GET("/api/v2/contact").and(accept(MediaType.APPLICATION_JSON_UTF8)), contactPersonHandler::getAllContactPersons)
                .andRoute(GET("/api/v2/contact/{id}").and(accept(MediaType.APPLICATION_JSON_UTF8)), contactPersonHandler::getContactPersonById)
                .andRoute(POST("/api/v2/contact").and(accept(MediaType.APPLICATION_JSON_UTF8)), contactPersonHandler::createContactPerson);

    }
}
