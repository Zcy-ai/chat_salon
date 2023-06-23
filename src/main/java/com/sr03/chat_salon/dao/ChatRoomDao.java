package com.sr03.chat_salon.dao;

import com.sr03.chat_salon.model.ChatRoom;

import java.util.List;

public interface ChatRoomDao {
    /**
     * Ajoute un salon de discussion
     *
     * @param chatRoom Le salon de discussion à ajouter
     * @return true si l'ajout est réussi, sinon false
     */
    boolean addChatRoom(ChatRoom chatRoom);

    /**
     * Supprime un salon de discussion par son ID
     *
     * @param chatRoomID L'ID du salon de discussion à supprimer
     * @return true si la suppression est réussie, sinon false
     */
    boolean deleteChatRoomByID(int chatRoomID);

    /**
     * Supprime un salon de discussion par son nom
     *
     * @param chatRoomName Le nom du salon de discussion à supprimer
     * @return true si la suppression est réussie, sinon false
     */
    boolean deleteChatRoomByName(String chatRoomName);

    /**
     * Récupère tous les salons de discussion
     *
     * @return La liste de tous les salons de discussion
     */
    List<ChatRoom> findAllChatRoom();

    /**
     * Recherche les salons de discussion par nom
     *
     * @param chatRoomName Le nom du salon de discussion à rechercher
     * @return La liste des salons de discussion correspondant au nom spécifié
     */
    List<ChatRoom> findChatRoomByName(String chatRoomName);

    /**
     * Récupère un salon de discussion par son ID
     *
     * @param chatRoomID L'ID du salon de discussion à récupérer
     * @return Le salon de discussion correspondant à l'ID spécifié, ou null si non trouvé
     */
    ChatRoom findChatRoomByID(int chatRoomID);

    /**
     * Récupère les salons de discussion d'un utilisateur
     *
     * @param userID L'ID de l'utilisateur
     * @return La liste des salons de discussion auxquels l'utilisateur est associé
     */
    List<ChatRoom> findChatRoomByUser(int userID);
}
