package com.sr03.chat_salon.dao.impl;

import com.sr03.chat_salon.dao.ContactDao;
import com.sr03.chat_salon.model.Contact;
import com.sr03.chat_salon.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContactDaoImpl implements ContactDao {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    @Transactional()
    public void addContact(Contact contact) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(contact);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contact> findAllContact() {
        Session session = this.sessionFactory.getCurrentSession();
        Query<Contact> query = session.createQuery("from Contact");
        return query.list();
    }
}
