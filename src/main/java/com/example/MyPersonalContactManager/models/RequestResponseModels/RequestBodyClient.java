package com.example.MyPersonalContactManager.models.RequestResponseModels;

import com.example.MyPersonalContactManager.models.ContactModels.Contact;
import com.example.MyPersonalContactManager.models.ContactModels.ContactDTOBig;
import com.example.MyPersonalContactManager.models.ContactModels.ContactDTOShort;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestBodyClient {
    @Valid
    public Contact contact;
    public ContactDTOShort contactDTOShort;
    public ContactDTOBig contactDTOBig;
    public String userId;
    public List<String> phoneNumberList;
    public List<Contact> contactList;

    public ContactDTOShort getContactDTO() {
        return contactDTOShort;
    }

    public void setContactDTO(ContactDTOShort contactDTOShort) {
        this.contactDTOShort = contactDTOShort;
    }
}
