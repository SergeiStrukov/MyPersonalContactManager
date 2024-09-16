package com.example.MyPersonalContactManager.models.ContactModels;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Phone {
    private String id;
    private String phoneNumber;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    @ManyToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;

    public Phone(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.createDate = LocalDateTime.now();
        this.lastUpdateDate = LocalDateTime.now();
    }

    public Phone() {
        this.createDate = LocalDateTime.now();
        this.lastUpdateDate = LocalDateTime.now();
    }
}
