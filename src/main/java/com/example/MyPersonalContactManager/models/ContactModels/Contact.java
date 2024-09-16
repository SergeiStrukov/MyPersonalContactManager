package com.example.MyPersonalContactManager.models.ContactModels;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID ownerId;
    private String firstName;
    private String lastName;
    private String email;
    private String birthday;
    private String address;
    private String photo;
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Phone> phonesList;
}
