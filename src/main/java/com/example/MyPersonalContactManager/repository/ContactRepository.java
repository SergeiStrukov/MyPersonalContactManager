package com.example.MyPersonalContactManager.repository;

import com.example.MyPersonalContactManager.models.ContactModels.Contact;
import com.example.MyPersonalContactManager.models.ContactModels.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {
    List<Contact> findByOwnerId(UUID id);

    Optional<Contact> findById(UUID id);

    @Query("SELECT p FROM Phone p WHERE p.contact.id = :contactId")
    List<Phone> getPhoneListByContactId(@Param("contactId") UUID contactId);

    List<Contact> findByUserId(UUID userId);

    @Query("SELECT c.ownerId FROM Contact c WHERE c.id = :id")
    UUID getOwnerId(@Param("id") UUID id);
}
