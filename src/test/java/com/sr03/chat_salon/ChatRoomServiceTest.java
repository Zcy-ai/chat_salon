package com.sr03.chat_salon;

//import com.sr03.chat_salon.dao.UserDao;
import com.sr03.chat_salon.dao.ChatRoomDao;
import com.sr03.chat_salon.dao.ContactDao;
import com.sr03.chat_salon.dao.UserDao;
import com.sr03.chat_salon.model.ChatRoom;
import com.sr03.chat_salon.service.ChatRoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.sr03.chat_salon.model.User;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChatRoomServiceTest {
    @Autowired
    private ChatRoomService chatRoomService;
    @Test
    void contextLoads() {
    }
    @Test
    public void addChatRoom() {
        ChatRoom chatRoom = new ChatRoom("SR03");
        chatRoomService.findAllChatRoom();
    }

    @Test
    public void findAllChatRoom() {
        chatRoomService.findAllChatRoom();
    }
    @Test
    public void findChatRoomByUser() {
        System.out.println(chatRoomService.findChatRoomByUser(1));
    }

}
