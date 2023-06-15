package com.sr03.chat_salon;

//import com.sr03.chat_salon.dao.UserDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sr03.chat_salon.dao.ChatRoomDao;
import com.sr03.chat_salon.dao.ContactDao;
import com.sr03.chat_salon.dao.UserDao;
import com.sr03.chat_salon.model.ChatMessage;
import com.sr03.chat_salon.model.ChatRoom;
import com.sr03.chat_salon.model.Contact;
import com.sr03.chat_salon.service.ChatHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.sr03.chat_salon.model.User;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChatSalonApplicationTests {
	@Autowired
	private UserDao userDao;
	@Autowired
	private ChatRoomDao chatRoomDao;
	@Autowired
	private ContactDao contactDao;
	@Autowired
	private ChatHistoryService chatHistoryService = new ChatHistoryService(new StringRedisTemplate());
	@Test
	void contextLoads() {
	}

	@Test
	public void addUser() {
		User user = new User("ZHA","Chenyi","zcy88827@gmail.com",1,"male", "1234");
		userDao.addUser(user);
		System.out.println(user);

	}
	@Test
	public void findUserByLogin() {
		System.out.println(userDao.findUserByLogin("zcy88827@163.com"));
	}
	@Test
	public void findAllUser() {
		System.out.println(userDao.findAllUser());
	}
	@Test
	public void updateUser() {
		User user = userDao.findUserByLogin("zcy88827@163.com");
		user.setPassword("123456789");
		userDao.updateUser(user);
		System.out.println(userDao.findUserByLogin("zcy88827@163.com"));
	}
	@Test
	public void addChatRoom() {
//		ChatRoom chat_room = new ChatRoom("WeChat");
//		chatRoomDao.addChatRoom(chat_room);
//		System.out.println(chatRoomDao.findAllChatRoom());
	}
//	@Test
//	public void TestContact() {
//		User user = userDao.findUserByLogin("zcy88827@gmail.com");
//		ChatRoom chatRoom = chatRoomDao.findChatRoomById(14);
//		Contact contact = new Contact(user,chatRoom);
//		contactDao.addContact(contact);
//		System.out.println(contactDao.findAllContact());
//	}
	@Test
	public void redis() throws JsonProcessingException {
//		chatHistoryService.addChatHistory("12", "zhacheny");
//		ChatMessage message = new ChatMessage(1, "zcy88827@gmail.com", "Chenyi", "ZHA", 12, "Hahahahaha");
//		chatHistoryService.addChatHistory("12", message);
		chatHistoryService.deleteChatHistory(41);
//		System.out.println(chatHistoryService.getChatHistoryByChatID(41));
	}

}
