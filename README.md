# chat_salon
## Introduction
Chat Salon est une application de chat en ligne qui permet aux utilisateurs de communiquer et d'interagir en temps réel. Il offre une plateforme conviviale et pratique pour faciliter les conversations.
Avec sa technologie avancée basée sur Java, React et Spring Boot, Chat Salon offre une expérience utilisateur fluide et réactive. Les fonctionnalités clés de l'application incluent la création de salles de discussion, l'historique des discussions, la gestion des contacts et bien plus encore.
## Tech stack
- 🐍JAVA
- ⚙️React
- 🛠️SpringBoot
- ⚡️SpringSecurity
- 🚗Redis
- ☁️Hibernate
- 🧘Restful API
- 💦websocket
- 🍓Thymeleaf

## l'architecture de l'application
![image](https://github.com/Zcy-ai/chat_salon/assets/75319382/d0a00651-9210-4485-821a-6f7d13ac6a42)
Notre architecture est une architecture de type frontend-backend, où le frontend et le backend sont séparés et communiquent entre eux via des `API REST` et `websocket`.

Le frontend est développé en utilisant `MaterialUI` et `ReactJS`. `MaterialUI` est une bibliothèque de composants d'interface utilisateur basée sur le design de Google Material Design, tandis que `ReactJS` est une bibliothèque JavaScript populaire pour la construction d'interfaces utilisateur interactives. Il se compose de page login, la page de registration et la page d'accueil de chatRoom.

Le backend est développé en utilisant Java et framework `Spring Boot`. `JWT` est un mécanisme de sécurité utilisé pour l'authentification et l'autorisation, chaque requête envoyé par client contient un token attribué et accordé par back-end ce qui nous permet de prévenir l'usurpation d'identité et d'autres attaques. `Spring Boot` est un framework Java qui facilite le développement d'applications backend. Hibernate est un framework de mappage objet-relationnel qui permet la persistance des données dans une base de données, qui nous permet de persister les données tels que l'utilisateur, chatRoom, contact, etc.

La base de données utilisée est `Redis` et `MySQL`. `Redis` est une base de données NoSQL en mémoire utilisée pour le stockage rapide de données qui nous permet d'enreigister les histoire de chat par chatRoom , tandis que `MySQL` est une base de données relationnelle classique utilisée pour le stockage structuré de données qui nous permet d'enregistrer l'utilisateur, chatRoom, contact.

Pour la communication entre le frontend et le backend, Axios est utilisé comme client HTTP pour envoyer des requêtes REST au backend. Les API REST permettent d'échanger des données entre le frontend et le backend de manière asynchrone. De plus, les websockets sont utilisés pour une communication en temps réel bidirectionnelle entre le frontend et le backend qui nous permet de réaliser le chat en temps réel.

### L'architecture de frontend
Le front-end est basé sur React.js, avec trois composants importants :
1. `Login` :<br/>
   Le composant Login est responsable de la fonctionnalité de connexion à l'application. Il contient un formulaire de connexion où les utilisateurs peuvent saisir leur adresse e-mail et leur mot de passe. Lorsque le formulaire est soumis, une requête est envoyée au serveur avec les informations de connexion fournies par l'utilisateur. Si la connexion est réussie (statut 200), l'utilisateur est redirigé vers la page de la salle de discussion principale (`ChatRoom`) avec les données de l'utilisateur, y compris la liste des salles de discussion disponibles et des utilisateurs. Si la connexion échoue, un message d'erreur approprié est affiché.
   <br/>
   ___Lorsqu'un utilisateur a saisi trois fois un mot de passe erroné, son statut devient "désactivé" et il ne peut plus se connecter___. Vous devrez demander à l'administrateur de modifier le statut de l'utilisateur à partir de l'interface d'administration afin de pouvoir continuer à se connecter.
3. `Register` :<br/>
   Le composant Register est utilisé pour l'inscription d'un utilisateur. Il fournit un formulaire avec plusieurs champs, notamment :

- `firstName` : Champ pour le prénom de l'utilisateur.
- `lastName` : Champ pour le nom de famille de l'utilisateur.
- `login` : Champ pour l'adresse e-mail ou le nom d'utilisateur de l'utilisateur.
- `password` : Champ pour le mot de passe de l'utilisateur.
- `confirmPassword` : Champ pour la confirmation du mot de passe de l'utilisateur.
- `gender` : Champ pour sélectionner le genre de l'utilisateur.
- `admin` : Champ pour sélectionner si l'utilisateur est un administrateur ou non.

   Le formulaire effectue des validations sur les champs, tels que la vérification de la présence et du format des données saisies. Si les données du formulaire sont valides, elles sont envoyées au serveur via une requête HTTP POST utilisant Axios. En cas de succès de l'inscription, l'utilisateur est redirigé vers la page de connexion.
3. `ChatRoom` :<br/>
   Le composant ChatRoom représente la salle de discussion principale de l'application où les utilisateurs peuvent communiquer en temps réel.
   Les principales caractéristiques de ce composant sont les suivantes :
- ***Connexion et déconnexion des utilisateurs :*** les utilisateurs peuvent se connecter et se déconnecter de l'application à l'aide de leurs informations d'identification.
- ***Rejoindre et quitter des salles de discussion :*** les utilisateurs peuvent rejoindre différentes salles de discussion et basculer entre elles pour communiquer avec d'autres utilisateurs.
- ***Envoi et réception de messages :*** les utilisateurs peuvent envoyer des messages dans une salle de discussion et recevoir les messages des autres utilisateurs en temps réel.
- ***Invitation d'utilisateurs :*** les utilisateurs peuvent inviter d'autres utilisateurs à rejoindre une salle de discussion spécifique en saisissant leur adresse e-mail.
- ***Gestion des invitations :*** les utilisateurs peuvent accepter ou refuser les invitations reçues pour rejoindre une salle de discussion.
- ***Suppression de salles de discussion :*** les utilisateurs peuvent supprimer les salles de discussion dont ils sont propriétaires.
### L'architecture de backend
1. `ChatSalonApplication.java` :
   Il s'agit de la classe d'entrée de l'application qui contient la méthode `main` pour démarrer l'application.

2. Package `utils` :
   Il contient des classes utilitaires, notamment `JwtTokenProvider.java`, qui fournit des méthodes pour gérer les JSON Web Tokens (JWT).

3. Package `service` :
   Il contient diverses interfaces de service et leurs implémentations pour gérer la logique métier. Par exemple, `ChatHistoryService.java` offre des méthodes de service liées à l'historique des discussions, `ChatRoomService.java` gère les opérations CRUD liées aux salons de discussion, `UserService.java` traite les opérations CRUD liées aux utilisateurs.

   Dans le sous-package `impl`, vous trouverez les implémentations concrètes des interfaces de service, telles que `ChatRoomServiceImpl.java`, `UserServiceImpl.java`, etc.

4. Package `model` :
   Il contient les classes de modèle utilisées dans l'application, telles que `ChatMessage.java` pour représenter un message de discussion, `ChatRoom.java` pour représenter un salon de discussion, `User.java` pour représenter un utilisateur, etc. Les classes du sous-package `resp` sont utilisées pour encapsuler les données de réponse.

5. Package `interceptor` :
   Il contient les classes d'intercepteur utilisées dans l'application. Par exemple, `UserInterceptor.java` intercepte les requêtes des utilisateurs et effectue le traitement approprié.

6. Package `dao` :
   Il contient les interfaces et implémentations des objets d'accès aux données (DAO) pour interagir avec la base de données. Par exemple, `ChatRoomDao.java` définit les méthodes d'accès aux données des salons de discussion, tandis que `ChatRoomDaoImpl.java` les implémente.

7. Package `controller` :
   Il contient les classes de contrôleur de l'application, qui gèrent les requêtes et les réponses. Chaque classe de contrôleur gère un module fonctionnel spécifique. Par exemple, `ChatHistoryController.java` gère les requêtes liées à l'obtention de l'historique des discussions, `ChatRoomServiceController.java` gère les requêtes liées aux services de salon de discussion, `UserServiceController.java` gère les requêtes liées aux services utilisateur.

8. Package `config` :
   Il contient les classes de configuration de l'application, telles que la configuration CORS globale qui nous permettre de résoudre le problème de CrossOrigin, la configuration Hibernate, la configuration des SpringSecurity, la configuration d'authentification JWT, la configuration de websocket, etc.

## La conception de BDD
<div align="center">
  <img src="assets/diagram.png" alt="Diagram">
</div>


La table "User" contient les informations relatives aux utilisateurs, telles que leur nom, prénom, login, mot de passe, genre, etc.

La table "ChatRoom" représente les salons de discussion et contient des informations sur le nom du salon et le chef du salon.

La table "Contact" est une classe d'association entre les utilisateurs et les salons de discussion, permettant de définir les contacts (utilisateurs participant à un salon de discussion donné). Elle contient des clés étrangères vers les tables "User" et "ChatRoom". 

### Justifications des choix de conception
La relation entre User et ChatRoom est une relation de plusieurs-à-plusieurs, car un utilisateur peut participer à plusieurs salons de discussion et un salon de discussion peut avoir plusieurs utilisateurs participants. Cela est représenté par une relation ManyToMany entre ces deux entités.

La classe Contact est introduite pour modéliser la relation entre User et ChatRoom, permettant de définir les contacts (utilisateurs participants) d'un salon de discussion donné.

Les annotations JPA telles que @Entity, @Table, @ManyToOne, @ManyToMany et @JoinColumn sont utilisées pour mapper les classes Java aux tables de la base de données et spécifier les relations entre les entités.

## Explications sur les interaction entre les différente technologies
## React et Spring :
Les interactions entre React et Spring se font via des API REST et Axios. React envoie des requêtes HTTP (GET, POST, PUT, DELETE) au backend développé avec Spring pour récupérer ou modifier des données. Le backend traite ces requêtes et renvoie des réponses, généralement sous forme de JSON et on a conçu quelques classes en tant que ResponseBody pour bien former les réponses. React peut alors utiliser ces données pour mettre à jour l'interface utilisateur de manière réactive.

### Fonctionnalité de chat
1. `POST http://localhost:8080/login`:
   Description: Cette API permet à un utilisateur de se connecter à l'application.

2. `POST http://localhost:8080/register`:
   Description: Cette API permet à un nouvel utilisateur de s'inscrire dans l'application.

3. `GET http://localhost:8080/chat_history/{{chatRoomID}}/{{token}}`:
   Description: Cette API permet de récupérer l'historique des discussions d'une salle de discussion spécifique, identifiée par l'ID de la salle de discussion.

4. `POST http://localhost:8080/create_chatroom/{{login}}/{{chatName}}/{{token}}`:
   Description: Cette API permet de créer une nouvelle salle de discussion avec un nom spécifié par l'utilisateur, en utilisant le login de l'utilisateur(qui est le chef de salle de discussion) et le token pour l'authentification.

5. `POST http://localhost:8080/delete_chatroom/{{user}}/{{chatRoomID}}/{{token}}`:
   Description: Cette API permet de supprimer une salle de discussion spécifique, identifiée par l'ID de la salle de discussion, en utilisant le login de l'utilisateur et le token pour l'authentification.

### Fonctionnalité de Admin
6. `GET http://localhost:8080/admin/createUser`:
   Description: Cette API permet à un administrateur de créer un nouvel utilisateur dans l'application.

7. `POST http://localhost:8080/admin/deleteUser/{{id}}`:
   Description: Cette API permet à un administrateur de supprimer un utilisateur spécifique, identifié par son ID.

8. `GET http://localhost:8080/admin/editUser/{{id}}`:
   Description: Cette API permet à un administrateur d'obtenir les informations d'un utilisateur spécifique pour les éditer.

9. `POST http://localhost:8080/admin/enableDisableUser/{{id}}`:
   Description: Cette API permet à un administrateur d'activer ou de désactiver un utilisateur spécifique, identifié par son ID.

10. `GET http://localhost:8080/admin/getAllUsers/{{status}}`:
    Description: Cette API permet à un administrateur d'obtenir la liste de tous les utilisateurs dans l'application, en fonction de leur statut (par exemple, actif, désactivé, etc.).

11. `POST http://localhost:8080/admin/login`:
    Description: Cette API permet à un administrateur de se connecter à l'interface d'administration de l'application.

12. `POST http://localhost:8080/admin/modifyUser`:
    Description: Cette API permet à un administrateur de modifier les informations d'un utilisateur spécifique.

13. `POST http://localhost:8080/admin/register`:
    Description: Cette API permet à un administrateur de faire le gestion de l'inscription=.
 
### Websocket et Spring :
Les websockets sont un protocole de communication bidirectionnelle en temps réel entre un navigateur et un serveur. Ils permettent une communication continue et instantanée entre le frontend et le backend. Spring prend en charge les websockets grâce au module Spring Websocket. Il fournit des fonctionnalités pour la création de points de terminaison websocket, la gestion des connexions et l'échange de messages en temps réel.

Lorsque l'utilisateur entre dans une salle de chat le connexion websocket de endpoint `ws://localhost:8080/chat/${currentLogin}/${currentChatRoomId}/${token}` est ouvert

Lorsqu'un utilisateur envoie un message dans une salle de discussion, le frontend l'envoie au backend via une connexion websocket. Le backend reçoit le message, le traite et le diffuse à tous les utilisateurs connectés à cette salle de discussion. Les utilisateurs connectés reçoivent ensuite le message en temps réel et peuvent le voir s'afficher dans l'interface utilisateur.

On a une fonction hook(useEffect) pour détecter si l'utilisateur change son salle de chat. s'il y a un changement de chatRoom, on va fermer le websocket connexion antérieur et ouvert un nouveau connexion.

En meme temps, On a un autre connexion websocket avec le endpoint `ws://localhost:8080/contact/${currentLogin}/${token}` qui est pour l'objectif de mettre en œuvre de la fonctionnalité d'inviter des utilisateurs dans des salons de discussion

## Résultat eco-index de site
### Plugin GreenIT
| Url                                          | Request number | Page Size (KB) | Dom Size | Greenhouse Gases Emission (gCO2e) | Water Consumption (cl) | EcoIndex | Grade |
|----------------------------------------------|----------------|----------------|----------|----------------------------------|------------------------|----------|-------|
| http://localhost:3000/login                   | 0              | 0              | 140      | 1.14                             | 1.71                   | 93.07    | A     |
| http://localhost:3000/register                | 0              | 0              | 44       | 1.05                             | 1.57                   | 97.66    | A     |
| http://localhost:3000/chatRoom                | 2              | 1              | 323      | 1.32                             | 1.97                   | 84.18    | A     |
| http://localhost:8080/admin/login             | 4              | 60             | 125      | 1.19                             | 1.79                   | 90.41    | A     |
| http://localhost:8080/admin/getAllUsers/all    | 0              | 0              | 125      | 1.13                             | 1.69                   | 93.51    | A     |
| http://localhost:8080/admin/getAllUsers/disabled | 0              | 0              | 80       | 1.10                             | 1.65                   | 94.85    | A     |
| http://localhost:8080/admin/createUser         | 0              | 0              | 8        | 1.09                             | 1.63                   | 95.63    | A     |
- Les URL "http://localhost:3000/login" et "http://localhost:3000/register" ont un EcoIndex élevé (93.07 et 97.66 respectivement), ce qui indique une bonne performance écologique. Cependant, la taille de la page pour "http://localhost:3000/login" est de 140 KB, ce qui pourrait être réduit pour améliorer davantage l'efficacité.
- L'URL "http://localhost:3000/chatRoom" a un nombre de requêtes élevé (2), ce qui peut être optimisé pour réduire les ressources utilisées et améliorer l'efficacité.
- Les URL liées à l'administration ("http://localhost:8080/admin/login", "http://localhost:8080/admin/getAllUsers/all", "http://localhost:8080/admin/getAllUsers/disabled" et "http://localhost:8080/admin/createUser") ont des EcoIndex élevés (entre 90.41 et 95.63), indiquant une bonne performance écologique. Cependant, la taille de la page pour "http://localhost:8080/admin/login" est de 60 KB, ce qui pourrait être optimisé pour réduire davantage la consommation de ressources.
### Avantages
- Nous utilisons JSON pour transmettre les données dans les requêtes, ce qui est plus rapide et dispose de plus de ressources CPU que XML.
### Pistes d'amélioration
- Stockage local
- Cache API
- Mettre en cache les réponses des APIs REST
- Mettre en cache le Bytecode
- Mettre en cache les objets souvent accédés en JavaScript
- Mettre en cache les données calculées souvent utilisées
- Utiliser des ORMs qui ont une bonne stratégie de mise en cache
- Utiliser en même temps plusieurs niveaux de cache
- Mettre les caches entièrement en RAM
## Sécurité
### JWT
JSON Web Token (JWT) est un standard ouvert (RFC 7519) qui permet de représenter de manière sécurisée des informations entre deux parties sous forme d'objet JSON. Il est utilisé pour l'authentification et l'autorisation dans les applications web.

Dans notre application Spring Boot, On configure Spring Security pour la manipulation de JWT. lorsque l'utilisateur login avec succès, un token JWT doit être généré et renvoyé au client grâce à `JWTTokenProvider`. Ce token contient des informations sur l'utilisateur et peut être utilisé pour vérifier l'identité de l'utilisateur lors des requêtes ultérieures.

Le token JWT est stocké de manière sécurisée côté client. Cela permet au client de l'envoyer automatiquement avec chaque requête.

À chaque requête du client, le serveur Spring Boot doit vérifier la validité, l'intégrité et expiration du token JWT en vérifiant la signature du token.

### SQL injection

On utilise des requêtes paramétrées : Au lieu de concaténer directement les valeurs dans vos requêtes SQL, utilisez des requêtes paramétrées avec des paramètres spécifiques pour les valeurs. Hibernate facilite cela en permettant l'utilisation de l'objet Query. Par exemple:
```java
String hql = "UPDATE User u SET u.firstName = :firstName, u.lastName = :lastName, u.gender = :gender, u.password = :pwd, u.admin = :admin, u.enabled = :enabled WHERE u.login = :login";
session.createQuery(hql)
 .setParameter("firstName", user.getFirstName())
 .setParameter("lastName", user.getLastName())
 .setParameter("gender", user.getGender())
 .setParameter("pwd", user.getPassword())
 .setParameter("enabled", user.isEnabled(), BooleanType.INSTANCE)
 .setParameter("admin", user.getAdmin(), BooleanType.INSTANCE)
 .setParameter("login", user.getLogin())
 .executeUpdate();
```
  
Hibernate ORM fournit une couche d'abstraction pour interagir avec la base de données. Utilisez les entités et les annotations JPA pour définir les relations et les requêtes entre vos objets et les tables de la base de données. Hibernate gère automatiquement l'encapsulation des valeurs et prévient les attaques d'injection SQL.

## Problèmes rencontrés
### rappel asynchron de reactjs

Par exemple, si on veut obtenir le dernier état et définir l'état dans un callback asynchrone ou une fermeture, la première façon d'obtenir l'état n'est pas en temps réel, et la documentation officielle de React mentionne que toute fonction à l'intérieur du composant, y compris les gestionnaires d'événements et les effets, est "vue" à partir du rendu dans lequel il a été créé, de sorte que la valeur référencée est toujours ancienne, ce qui conduit éventuellement à une exception avec setState.

Solution:

```javascript
setChatRoom((prevChats) => {
   const updatedChats = prevChats.filter((chat) => chat.id !== chatRoomID);
   return updatedChats;
});
```


### Problème de chargement paresseux(lazy loading) Hibernate et solution :

Hibernate utilise la stratégie de chargement paresseux par défaut pour optimiser les performances en évitant de charger toutes les données associées lors du chargement initial d'une entité.

Cependant, cela peut poser un problème lorsque vous accédez à des données associées qui n'ont pas encore été chargées. Cela se produit généralement lorsqu'une entité a une relation avec une autre entité et qu'on essaye d'accéder aux données de la relation sans avoir explicitement chargé ces données.

On a une association `ManyToMany` entre chatRoom et User, donc on crée une classe d'association contact quand on a besoin de deux donnes dans le meme temps et bien evite le problèmes de lazy loading.
