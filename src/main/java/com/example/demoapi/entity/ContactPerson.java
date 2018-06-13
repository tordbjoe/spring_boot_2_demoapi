package com.example.demoapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Document(collection = "contactpersons")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactPerson {

    @Id
    private String id;

    @NotBlank
    @Size(max = 130)
    private String name;

    private String position;

    private String phone;

    @NotNull
    private String email;

    private String company;

    private String department;

}
