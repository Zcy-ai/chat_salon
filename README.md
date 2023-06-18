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
### react
### spring
### websocket

### redis

## Résultat eco-index de votre site (plugin greenit) et des pistes d'amélioration 密
## Sécurité


## Problèmes rencontrés
https://facingissuesonit.com/2019/07/17/com-fasterxml-jackson-databind-exc-invaliddefinitionexception-cannot-construct-instance-of-xyz-no-creators-like-default-construct-exist-cannot-deserialize-from-object-value-no-delega/ 
json问题
useEffect不带onopen onclose就接收不到消息
https://blog.csdn.net/kxj19980524/article/details/88751114

javax的websocket不能autowired spring的bean的问题

https://stackoverflow.com/questions/52496677/springboot-websocket-do-not-work-autowired

error:Could not write JSON: failed to lazily initialize a collection of role: com.sr03.chat_salon.model.ChatRoom.users, could not initialize proxy - no Session; nested exception is com.fasterxml.jackson.databind.JsonMappingException: failed to lazily initialize a collection of role: com.sr03.chat_salon.model.ChatRoom.users, could not initialize proxy - no Session (through reference chain: com.sr03.chat_salon.model.resp.UserLoginResp["chatRoomList"]->java.util.ArrayList[0]->com.sr03.chat_salon.model.ChatRoom["users"])

https://stackoverflow.com/questions/70137035/could-not-write-json-failed-to-lazily-initialize-a-collection-of-role

useState闭包问题
https://www.jianshu.com/p/ca1e367af8a7


创建新的api后不要忘记拦截器内部的修改

Hibernate延迟加载问题 Hibernate中主要是通过代理（proxy）机制来实现延迟加载。它的具体过程：Hibernate丛数据库获取某一个对象数据时、获取某一 个对象的集合属性值时，或获取某一个对象所关联的另一个对象时，由于没有使用该对象的数据，hibernate并不是数据库加载真正的数据，而只是为该对 象创建一个代理对象来代表这个对象，这个对象上的所有属性都是默认值；只有在真正需要使用该对象的数据时才创建这个真实对象，真正从数据库中加载它的数 据，这样在某些情况下，就可以提高查询效率。

