package com.sr03.chat_salon.service.impl;

import com.sr03.chat_salon.dao.ContactDao;
import com.sr03.chat_salon.model.Contact;
import com.sr03.chat_salon.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactDao contactDao;
    @Override
    public Contact addContact(Contact contact) {
        return contactDao.addContact(contact);
    }

    @Override
    public void deleteContact(int userID, int chatRoomID) {contactDao.deleteContact( userID, chatRoomID);};

    @Override
    public List<Contact> findAllContact() {
        return contactDao.findAllContact();
    }

    @Override
    public List<Contact> findContactByChatRoom(int chatRoomID) {
        return contactDao.findContactByChatRoom(chatRoomID);
    }

    @Override
    public List<Contact> findContactByUser(int userID) {
        return contactDao.findContactByUser(userID);
    }

    @Override
    public Contact findContactByChatRoomLogin(int chatRoomID, String login){return contactDao.findContactByChatRoomLogin(chatRoomID, login);}
}
