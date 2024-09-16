package com.example.MyPersonalContactManager.service;

import java.util.List;
import java.util.UUID;

public interface ContactServiceInterface<T, U> {
    T getContactById(UUID contactId);

    List<T> getContactByUserId(UUID userId);

    List<T> getAllContacts();

    T createContact(T contact, UUID userId);

    U updateContact(UUID contactId, U newContact);

    boolean deleteContactById(UUID contactId);

    UUID getOwnerId(UUID contactId);
}
