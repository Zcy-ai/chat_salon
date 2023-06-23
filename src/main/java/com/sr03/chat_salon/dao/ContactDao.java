package com.sr03.chat_salon.dao;

import com.sr03.chat_salon.model.Contact;

import java.util.List;

public interface ContactDao {
    /**
     * Ajoute un contact
     *
     * @param contact Le contact à ajouter
     * @return L'objet contact après l'ajout
     */
    Contact addContact(Contact contact);

    /**
     * Supprime un contact
     *
     * @param userID     L'ID de l'utilisateur
     * @param chatRoomID L'ID du salon de discussion
     */
    void deleteContact(int userID, int chatRoomID);

    /**
     * Récupère tous les contacts
     *
     * @return La liste de tous les contacts
     */
    List<Contact> findAllContact();

    /**
     * Récupère les contacts d'un utilisateur
     *
     * @param userID L'ID de l'utilisateur
     * @return La liste des contacts de l'utilisateur
     */
    List<Contact> findContactByUser(int userID);

    /**
     * Récupère les contacts d'un salon de discussion
     *
     * @param chatRoomID L'ID du salon de discussion
     * @return La liste des contacts du salon de discussion
     */
    List<Contact> findContactByChatRoom(int chatRoomID);

    /**
     * Récupère le contact d'un utilisateur dans un salon de discussion
     *
     * @param chatRoomID L'ID du salon de discussion
     * @param login      Le nom d'utilisateur
     * @return L'objet Contact correspondant au salon de discussion et à l'utilisateur spécifiés, ou null si non trouvé
     */
    Contact findContactByChatRoomLogin(int chatRoomID, String login);
}
