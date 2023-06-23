package com.sr03.chat_salon.service;

import com.sr03.chat_salon.model.ChatRoom;

import java.util.List;

public interface ChatRoomService {
    /**
     * Ajoute une salle de discussion.
     *
     * @param chatRoom La salle de discussion à ajouter.
     * @return La salle de discussion ajoutée.
     */
    ChatRoom addChatRoom(ChatRoom chatRoom);

    /**
     * Supprime une salle de discussion en fonction de son ID.
     *
     * @param chatRoomID L'ID de la salle de discussion à supprimer.
     * @return true si la suppression a réussi, false sinon.
     */
    boolean deleteChatRoomByID(int chatRoomID);

    /**
     * Supprime une salle de discussion en fonction de son nom.
     *
     * @param chatRoomName Le nom de la salle de discussion à supprimer.
     * @return true si la suppression a réussi, false sinon.
     */
    boolean deleteChatRoomByName(String chatRoomName);

    /**
     * Récupère toutes les salles de discussion.
     *
     * @return La liste de toutes les salles de discussion.
     */
    List<ChatRoom> findAllChatRoom();

    /**
     * Recherche des salles de discussion en fonction de leur nom.
     *
     * @param chatRoomName Le nom de la salle de discussion.
     * @return La liste des salles de discussion correspondant au nom donné.
     */
    List<ChatRoom> findChatRoomByName(String chatRoomName);

    /**
     * Récupère une salle de discussion en fonction de son ID.
     *
     * @param chatRoomID L'ID de la salle de discussion.
     * @return La salle de discussion correspondant à l'ID donné.
     */
    ChatRoom findChatRoomByID(int chatRoomID);

    /**
     * Récupère les salles de discussion d'un utilisateur spécifique.
     *
     * @param userID L'ID de l'utilisateur.
     * @return La liste des salles de discussion de l'utilisateur.
     */
    List<ChatRoom> findChatRoomByUser(int userID);
}
