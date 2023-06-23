package com.sr03.chat_salon.service;

import com.sr03.chat_salon.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    /**
     * Enregistre un utilisateur.
     *
     * @param user L'utilisateur à enregistrer.
     * @return L'utilisateur enregistré.
     */
    User registerUser(User user);

    /**
     * Connecte un utilisateur en utilisant le nom d'utilisateur et le mot de passe.
     *
     * @param login    L'email d'utilisateur.
     * @param password Le mot de passe.
     * @return L'utilisateur connecté.
     */
    User login(String login, String password);

    /**
     * Récupère tous les utilisateurs.
     *
     * @return La liste de tous les utilisateurs.
     */
    List<User> getAllUsers();

    /**
     * Recherche les utilisateurs en fonction de la requête de recherche, de la pagination et du tri.
     *
     * @param searchQuery La requête de recherche.
     * @param pageable    Les informations de pagination et de tri.
     * @param sortBy      L'attribut selon lequel trier les résultats.
     * @return La page des utilisateurs correspondant aux critères de recherche.
     */
    Page<User> searchUsers(String searchQuery, Pageable pageable, String sortBy);

    /**
     * Récupère tous les utilisateurs avec la pagination et le tri spécifiés.
     *
     * @param pageable Les informations de pagination et de tri.
     * @param sortBy   L'attribut selon lequel trier les résultats.
     * @return La page des utilisateurs.
     */
    Page<User> getAllUsers(Pageable pageable, String sortBy);

    /**
     * Récupère les utilisateurs désactivés avec la pagination et le tri spécifiés.
     *
     * @param pageable Les informations de pagination et de tri.
     * @param sortBy   L'attribut selon lequel trier les résultats.
     * @return La page des utilisateurs désactivés.
     */
    Page<User> getDisabledUsers(Pageable pageable, String sortBy);

    /**
     * Recherche un utilisateur en fonction du nom d'utilisateur.
     *
     * @param login L'email d'utilisateur.
     * @return L'utilisateur correspondant au nom d'utilisateur donné.
     */
    User findUserByLogin(String login);

    /**
     * Recherche un utilisateur en fonction de l'ID.
     *
     * @param id L'ID de l'utilisateur.
     * @return L'utilisateur correspondant à l'ID donné.
     */
    User findUserById(int id);

    /**
     * Recherche les utilisateurs d'une salle de discussion spécifique.
     *
     * @param chatRoomID L'ID de la salle de discussion.
     * @return La liste des utilisateurs de la salle de discussion.
     */
    List<User> findUserByChatRoom(int chatRoomID);

    /**
     * Ajoute un utilisateur.
     *
     * @param user L'utilisateur à ajouter.
     */
    void addUser(User user);

    /**
     * Supprime un utilisateur en fonction du nom d'utilisateur.
     *
     * @param login Le nom d'utilisateur de l'utilisateur à supprimer.
     */
    void deleteUserByLogin(String login);

    /**
     * Supprime un utilisateur en fonction de l'ID.
     *
     * @param id L'ID de l'utilisateur à supprimer.
     */
    void deleteUserById(int id);

    /**
     * Active ou désactive un utilisateur en fonction de l'ID.
     *
     * @param id L'ID de l'utilisateur à activer ou désactiver.
     */
    void enableDisableById(int id);

    /**
     * Vérifie l'authentification d'un utilisateur en utilisant le nom d'utilisateur et le mot de passe.
     *
     * @param login    Le nom d'utilisateur.
     * @param password Le mot de passe.
     * @return true si l'authentification est réussie, sinon false.
     */
    boolean authenticate(String login, String password);

    /**
     * Met à jour les informations d'un utilisateur.
     *
     * @param user L'utilisateur à mettre à jour.
     */
    void updateUser(User user);

    /**
     * Modifie les informations d'un utilisateur.
     *
     * @param user L'utilisateur à modifier.
     */
    void modifyUser(User user);
}
