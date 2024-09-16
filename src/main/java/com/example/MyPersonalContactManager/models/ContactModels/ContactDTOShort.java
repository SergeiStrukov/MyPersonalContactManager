package com.example.MyPersonalContactManager.models.ContactModels;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ContactDTOShort {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    private String firstName;

    @Size(min = 1)
    private List<String> phones;

    public ContactDTOShort(String firstName, List<String> phones) {
        this.firstName = firstName;
        this.phones = phones;
    }

}
