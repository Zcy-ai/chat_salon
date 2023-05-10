package com.sr03.chat_salon;

//import com.sr03.chat_salon.dao.UserDao;
import com.sr03.chat_salon.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.sr03.chat_salon.model.User;

@SpringBootTest
class ChatSalonApplicationTests {
	@Autowired
	private UserDao userDao;
	@Test
	void contextLoads() {
	}

	@Test
	public void addUser() {
		User user = new User("ZHA","Chenyi","zcy88827@163.com",1,"male", "1234");
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
}
