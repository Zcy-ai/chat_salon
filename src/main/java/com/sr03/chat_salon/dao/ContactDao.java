package com.sr03.chat_salon.dao;

import com.sr03.chat_salon.model.Contact;

import java.util.List;

public interface ContactDao {
    void addContact(Contact contact);
    List<Contact> findAllContact();
}
