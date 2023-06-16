package com.sr03.chat_salon.dao;

import com.sr03.chat_salon.model.Contact;

import java.util.List;

public interface ContactDao {
    Contact addContact(Contact contact);
    void deleteContact(int userID, int chatRoomID);
    List<Contact> findAllContact();
    List<Contact> findContactByUser(int userID);
    List<Contact> findContactByChatRoom(int chatRoomID);
    Contact findContactByChatRoomLogin(int chatRoomID, String login);
}
