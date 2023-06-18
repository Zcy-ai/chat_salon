# chat_salon
## Introduction
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

### L'architeccture de frontend 密
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

## La conception (diagramme de classes, schéma relationnel, justifier brièvement vos choix) 密
### 

## Explications sur les interaction entre les différente technologies : react, spring et web socket
## React et Spring :
Les interactions entre React et Spring se font via des API REST et Axios. React envoie des requêtes HTTP (GET, POST, PUT, DELETE) au backend développé avec Spring pour récupérer ou modifier des données. Le backend traite ces requêtes et renvoie des réponses, généralement sous forme de JSON et on a conçu quelques classes en tant que ResponseBody pour bien former les réponses. React peut alors utiliser ces données pour mettre à jour l'interface utilisateur de manière réactive.
`POST http://localhost:8080/login`
POST http://localhost:8080/register
{{token}}
### Websocket et Spring :
Les websockets sont un protocole de communication bidirectionnelle en temps réel entre un navigateur et un serveur. Ils permettent une communication continue et instantanée entre le frontend et le backend. Spring prend en charge les websockets grâce au module Spring Websocket. Il fournit des fonctionnalités pour la création de points de terminaison websocket, la gestion des connexions et l'échange de messages en temps réel.

Lorsque l'utilisateur entre dans une salle de chat le connexion websocket de endpoint `ws://localhost:8080/chat/${currentLogin}/${currentChatRoomId}/${token}` est ouvert

Lorsqu'un utilisateur envoie un message dans une salle de discussion, le frontend l'envoie au backend via une connexion websocket. Le backend reçoit le message, le traite et le diffuse à tous les utilisateurs connectés à cette salle de discussion. Les utilisateurs connectés reçoivent ensuite le message en temps réel et peuvent le voir s'afficher dans l'interface utilisateur.

On a une fonction hook(useEffect) pour détecter si l'utilisateur change son salle de chat. s'il y a un changement de chatRoom, on va fermer le websocket connexion antérieur et ouvert un nouveau connexion.

En meme temps, On a un autre connexion websocket avec le endpoint `ws://localhost:8080/contact/${currentLogin}/${token}` qui est pour l'objectif de mettre en œuvre de la fonctionnalité d'inviter des utilisateurs dans des salons de discussion

## Résultat eco-index de votre site (plugin greenit) et des pistes d'amélioration 密
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
Si on utilise un hook d'effet (`useEffect`) pour effectuer une opération asynchrone pour ajouter les élement dans la liste, on vois que La liste à laquelle on aboutient n'est pas celle qu'on pense.

```javascript
const [isMounted, setIsMounted] = useState(true);
```

2. À l'intérieur de votre hook d'effet, utilisez la variable `isMounted` comme dépendance et une fonction de rappel annulée pour vérifier si le composant est toujours monté avant de mettre à jour l'état. Vous pouvez utiliser la fonction `return` du hook d'effet pour annuler l'opération asynchrone si le composant est démonté pendant son exécution.


### Problème de chargement paresseux(lazy loading) Hibernate et solution :

Hibernate utilise la stratégie de chargement paresseux par défaut pour optimiser les performances en évitant de charger toutes les données associées lors du chargement initial d'une entité.

Cependant, cela peut poser un problème lorsque vous accédez à des données associées qui n'ont pas encore été chargées. Cela se produit généralement lorsqu'une entité a une relation avec une autre entité et qu'on essaye d'accéder aux données de la relation sans avoir explicitement chargé ces données.

On a une association `ManyToMany` entre chatRoom et User, donc on crée une classe d'association contact quand on a besoin de deux donnes dans le meme temps et bien evite le problèmes de lazy loading.
