package com.sr03.chat_salon.service;

import com.sr03.chat_salon.model.Contact;

import java.util.List;

public interface ContactService {
    Contact addContact(Contact contact);
//    void deleteContactB
    List<Contact> findAllContact();
    List<Contact> findContactByChatRoom(int chatRoomID);
    List<Contact> findContactByUser(int userID);
//    Contact findContactByUserChatRoom(int userID, int chatRoomID);
}
