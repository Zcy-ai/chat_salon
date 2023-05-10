package com.sr03.chat_salon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class ChatSalonApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatSalonApplication.class, args);
	}

}
