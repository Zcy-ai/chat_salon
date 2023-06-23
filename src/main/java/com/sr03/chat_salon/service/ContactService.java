package com.sr03.chat_salon.service;

import com.sr03.chat_salon.model.Contact;

import java.util.List;

public interface ContactService {
    /**
     * Ajoute un contact.
     *
     * @param contact Le contact à ajouter.
     * @return Le contact ajouté.
     */
    Contact addContact(Contact contact);

    /**
     * Supprime un contact en fonction de l'ID de l'utilisateur et de l'ID de la salle de discussion.
     *
     * @param userID     L'ID de l'utilisateur.
     * @param chatRoomID L'ID de la salle de discussion.
     */
    void deleteContact(int userID, int chatRoomID);

    /**
     * Récupère tous les contacts.
     *
     * @return La liste de tous les contacts.
     */
    List<Contact> findAllContact();

    /**
     * Récupère les contacts d'une salle de discussion spécifique.
     *
     * @param chatRoomID L'ID de la salle de discussion.
     * @return La liste des contacts de la salle de discussion.
     */
    List<Contact> findContactByChatRoom(int chatRoomID);

    /**
     * Récupère les contacts d'un utilisateur spécifique.
     *
     * @param userID L'ID de l'utilisateur.
     * @return La liste des contacts de l'utilisateur.
     */
    List<Contact> findContactByUser(int userID);

    /**
     * Recherche un contact en fonction de l'ID de la salle de discussion et du nom d'utilisateur.
     *
     * @param chatRoomID L'ID de la salle de discussion.
     * @param login      L'email d'utilisateur.
     * @return Le contact correspondant à l'ID de la salle de discussion et au nom d'utilisateur donnés.
     */
    Contact findContactByChatRoomLogin(int chatRoomID, String login);
}
