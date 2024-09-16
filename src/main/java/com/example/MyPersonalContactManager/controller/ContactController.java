package com.example.MyPersonalContactManager.controller;

import com.example.MyPersonalContactManager.models.ContactModels.Contact;
import com.example.MyPersonalContactManager.models.ContactModels.ContactDTOBig;
import com.example.MyPersonalContactManager.service.DatabaseContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private DatabaseContactService dbContactService; // Исправлено на правильный сервис

    @PostMapping
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact, @RequestParam UUID userId) {
        Contact createdContact = dbContactService.createContact(contact, userId); // Используем сервис
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContact);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable UUID id) {
        Contact contact = dbContactService.getContactById(id); // Используем сервис
        return ResponseEntity.ok(contact);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Contact>> getContactsByUserId(@PathVariable UUID userId) {
        List<Contact> contacts = dbContactService.getContactByUserId(userId); // Используем сервис
        return ResponseEntity.ok(contacts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDTOBig> updateContact(@PathVariable UUID id, @RequestBody ContactDTOBig contact) {
        ContactDTOBig updatedContact = dbContactService.updateContact(id, contact); // Используем сервис
        return ResponseEntity.ok(updatedContact);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteContact(@PathVariable UUID id) {
        boolean deleted = dbContactService.deleteContactById(id); // Используем сервис
        return ResponseEntity.ok(deleted);
    }
}
