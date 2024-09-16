package com.example.MyPersonalContactManager.service;

import com.example.MyPersonalContactManager.exceptions.ContactNotFoundException;
import com.example.MyPersonalContactManager.models.ContactModels.Contact;
import com.example.MyPersonalContactManager.models.ContactModels.ContactDTOBig;
import com.example.MyPersonalContactManager.repository.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DatabaseContactService implements ContactServiceInterface<Contact, ContactDTOBig> {

    private final ContactRepository dbRepository;

    @Override
    @Transactional(readOnly = true)
    public Contact getContactById(UUID contactId) {
        return dbRepository.findById(contactId)
                .map(contact -> {
                    List<String> phoneList = dbRepository.getPhoneListByContactId(contactId);
                    contact.setPhones(phoneList);
                    return contact;
                })
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with id: " + contactId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contact> getContactByUserId(UUID userId) {
        List<Contact> contacts = dbRepository.findByUserId(userId);
        for (Contact contact : contacts) {
            List<String> phoneList = dbRepository.getPhoneListByContactId(contact.getId());
            contact.setPhones(phoneList);
        }
        return contacts;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contact> getAllContacts() {
        List<Contact> contacts = dbRepository.findAll();
        for (Contact contact : contacts) {
            List<String> phoneList = dbRepository.getPhoneListByContactId(contact.getId());
            contact.setPhones(phoneList);
        }
        return contacts;
    }

    @Override
    @Transactional
    public Contact createContact(Contact contact, UUID ownerId) {
        contact.setOwnerId(ownerId); // Присвойте userId контакту
        return dbRepository.save(contact); // Используйте save для сохранения контакта
    }

    @Override
    @Transactional
    public ContactDTOBig updateContact(UUID contactId, ContactDTOBig newContact) {
        Contact existingContact = dbRepository.findById(contactId)
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with id: " + contactId));

        // Обновите существующий контакт с новыми данными
        existingContact.setFirstName(newContact.getFirstName());
        existingContact.setLastName(newContact.getLastName());
        existingContact.setEmail(newContact.getEmail());
        existingContact.setPhones(newContact.getPhones());

        // Преобразуйте LocalDate в String для birthday
        existingContact.setBirthday(newContact.getBirthday() != null ? newContact.getBirthday().toString() : null);
        existingContact.setAddress(newContact.getAddress());
        existingContact.setPhoto(newContact.getPhoto());

        dbRepository.save(existingContact); // Сохраните обновленный контакт
        return newContact;
    }

    @Override
    @Transactional
    public boolean deleteContactById(UUID contactId) {
        if (!dbRepository.existsById(contactId)) {
            throw new ContactNotFoundException("Contact not found with id: " + contactId);
        }
        dbRepository.deleteById(contactId);
        return true;
    }

    @Override
    public UUID getOwnerId(UUID contactId) {
        return dbRepository.getOwnerId(contactId);
    }
}
