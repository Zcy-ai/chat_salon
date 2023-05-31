package com.sr03.chat_salon;

//import com.sr03.chat_salon.dao.UserDao;
import com.sr03.chat_salon.dao.ChatRoomDao;
import com.sr03.chat_salon.dao.ContactDao;
import com.sr03.chat_salon.dao.UserDao;
import com.sr03.chat_salon.model.ChatRoom;
import com.sr03.chat_salon.model.Contact;
import com.sr03.chat_salon.model.resp.ChatRoomResp;
import com.sr03.chat_salon.service.ChatRoomService;
import com.sr03.chat_salon.service.ContactService;
import com.sr03.chat_salon.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.sr03.chat_salon.model.User;

import java.util.List;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChatRoomServiceTest {
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private UserService userService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private ChatRoomDao chatRoomDao;
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

    @Test
    public void createChatHandlerTest() {
        User user = userService.findUserByLogin("yunrong.ruan@gmail.com");
        ChatRoom chatRoom = chatRoomService.findChatRoomByID(14);
//        chatRoom.addUser(user);
        Contact contact = new Contact(user, chatRoom);
        contactService.addContact(contact);
        List<ChatRoom> chatRoomList = chatRoomService.findChatRoomByUser(user.getId());
        ChatRoomResp resp = new ChatRoomResp(chatRoomList);
        System.out.println(resp);
    }

    @Test
    public void deleteChatRoomHandlerTest() {
        // TODO 显示运行成功，但没有在数据库中删除ChatRoom
        User user = userService.findUserByLogin("zcy88827@gmail.com");
        chatRoomService.deleteChatRoomByID(19);
        List<ChatRoom> chatRoomList = chatRoomService.findChatRoomByUser(user.getId());
        ChatRoomResp resp = new ChatRoomResp(chatRoomList);
        System.out.println(resp);
    }

    @Test
    public void deleteTest() {
        chatRoomDao.deleteChatRoomByID(19);
    }
    @Test
    public void test() {
        List<ChatRoom> chatRoomList = chatRoomService.findChatRoomByUser(13);
        for(int i=0;i<chatRoomList.size();i++) {
            System.out.println(chatRoomList.get(i).getId()+chatRoomList.get(i).getName()+chatRoomList.get(i).getUsers());
        }
    }
}
