package com.sr03.chat_salon;

import com.sr03.chat_salon.dao.UserDao;
import com.sr03.chat_salon.dao.UserDaoCustom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.sr03.chat_salon.model.User;

import java.util.List;

@SpringBootTest
class ChatSalonApplicationTests {
	@Autowired
	private UserDaoCustom userDaoCustom;
	@Autowired
	private UserDao userDao;
	@Test
	void contextLoads() {
	}

	@Test
	public void findAll() {

	}

	@Test
	public void deleteUser() {
		userDao.deleteById(1);
	}
	@Test
	public void findAllUser() {
		List<User> userList = userDao.findAll();
		System.out.println(userList);
	}
	@Test
	public void createUser() {
		User user_1 = userDaoCustom.CreateUser("Chenyi", "ZHA", "zcy88827@163.com", 1, "male", "123456");
		User user_2 = userDaoCustom.CreateUser("Chenwei", "Mi", "lymickey@163.com", 1, "male", "123456");
	}

}
