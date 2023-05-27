package com.sr03.chat_salon.dao;

import com.sr03.chat_salon.model.Contact;

import java.util.List;

public interface ContactDao {
    Contact addContact(Contact contact);
    List<Contact> findAllContact();
    List<Contact> findContactByUser(int userID);
    List<Contact> findContactByChatRoom(int chatRoomID);
}
