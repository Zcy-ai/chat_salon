package com.sr03.chat_salon.dao;

import com.sr03.chat_salon.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserDao {
    /**
     * Ajoute un utilisateur
     *
     * @param user L'utilisateur à ajouter
     */
    void addUser(User user);

    /**
     * Supprime un utilisateur par son ID
     *
     * @param id L'ID de l'utilisateur à supprimer
     */
    void deleteUserById(int id);

    /**
     * Supprime un utilisateur par login d'utilisateur
     *
     * @param login  d'utilisateur de l'utilisateur à supprimer
     */
    void deleteUserByLogin(String login);

    /**
     * Met à jour les informations d'un utilisateur
     *
     * @param user L'utilisateur à mettre à jour
     */
    void updateUser(User user);

    /**
     * Modifie les informations d'un utilisateur
     *
     * @param user L'utilisateur à modifier
     */
    void modifyUser(User user);

    /**
     * Active ou désactive un utilisateur par son ID
     *
     * @param id L'ID de l'utilisateur à activer ou désactiver
     */
    void enableDisableById(int id);

    /**
     * Récupère un utilisateur par son nom d'utilisateur
     *
     * @param login d'utilisateur de l'utilisateur à récupérer
     * @return L'utilisateur correspondant au login d'utilisateur spécifié, ou null si non trouvé
     */
    User findUserByLogin(String login);

    /**
     * Récupère un utilisateur par son ID
     *
     * @param id L'ID de l'utilisateur à récupérer
     * @return L'utilisateur correspondant à l'ID spécifié, ou null si non trouvé
     */
    User findUserById(int id);

    /**
     * Récupère tous les utilisateurs
     *
     * @return La liste de tous les utilisateurs
     */
    List<User> findAllUser();

    /**
     * Récupère les utilisateurs d'un salon de discussion
     *
     * @param chatRoomID L'ID du salon de discussion
     * @return La liste des utilisateurs du salon de discussion
     */
    List<User> findUserByChatRoom(int chatRoomID);

    /**
     * Recherche les utilisateurs correspondant à une requête de recherche
     *
     * @param searchQuery La requête de recherche
     * @param pageable    Les informations de pagination
     * @param sortBy      Le critère de tri
     * @return Une page contenant les utilisateurs correspondant à la requête de recherche
     */
    Page<User> searchUsers(String searchQuery, Pageable pageable, String sortBy);

    /**
     * Récupère tous les utilisateurs avec pagination et tri
     *
     * @param pageable Les informations de pagination
     * @param sortBy   Le critère de tri
     * @return Une page contenant les utilisateurs
     */
    Page<User> findAll(Pageable pageable, String sortBy);

    /**
     * Récupère les utilisateurs désactivés avec pagination et tri
     *
     * @param pageable Les informations de pagination
     * @param sortBy   Le critère de tri
     * @return Une page contenant les utilisateurs désactivés
     */
    Page<User> findDisabledUsers(Pageable pageable, String sortBy);
}