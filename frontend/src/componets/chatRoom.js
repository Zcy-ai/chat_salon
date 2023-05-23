import { useLocation } from 'react-router-dom';
import '../chatRoom.css';
import img from '../images/user.svg';
import {useEffect, useState} from "react";
class MessageToSend {
    constructor(sender, content) {
        this.sender = sender;
        this.content = content;
    }
}
function ChatRoom() {
    let location = useLocation();
    let userList = location.state.userList;
    let currentFirstName = location.state.firstName;
    let currentLastName = location.state.lastName;
    let [message, setMessage] = useState('');
    let [chatMessages, setChatMessages] = useState([]);
    const socket = new WebSocket("ws://localhost:8080/chat/"+currentLastName+"_"+currentFirstName);
    // 处理消息发送
    const sendMessage = () => {
        if (message.trim() !== '') {
            //发送消息
            const newMessage = new MessageToSend(
                currentFirstName + ' ' + currentLastName,
                message
            );
            socket.send(JSON.stringify(newMessage));
            setMessage('');
        }
    };
    // 处理接收和显示消息
    useEffect(() => {
        socket.onopen = (event) => {
        };
        socket.onmessage = (event) => {
            const receivedMessage = JSON.parse(event.data);
            const { sender, content } = receivedMessage;
            const formattedMessage = `${sender}: ${content}`;
            const currentDate = new Date().toLocaleDateString(); // 添加日期
            setChatMessages((prevMessages) => [...prevMessages, { message: formattedMessage, date: currentDate }]);
        };
        socket.onclose = function (event) {
            console.log("Socket closed");
        };
        socket.onerror = function (err) {
            console.log('Socket encountered error: ', err.message, 'Closing socket');
            // setWebSocketReady(false);
            socket.close();
        };
    }, [socket]);

    return (
        <div className="container-chat">
            <div className="user-information">
                <div className="user-profile">
                    <img className="avatar" src={img} alt="Avatar" />
                    <div className="user-current">{currentFirstName} {currentLastName}</div>
                    <button className="logout">Logout</button>
                </div>
                <div className="online-users">
                    {/* 现在是全部用户而不是在线用户 */}
                    <h2>Online Users</h2>
                    {userList.map(user => (
                        <div key={user.id}>
                        <img className="avatar" src={img} alt="Avatar" />
                        <p className="name">{user.firstName} {user.lastName}</p>
                        </div>
                    ))}
                </div>
            </div>
            <div className="chat-room">
                <h2>Chat Room</h2>
                <div className="message-area">
                    {chatMessages.map((chatMessage, index) => (
                        <p key={index}>
                            <span className="message-content">{chatMessage.message}</span>
                            <span className="message-date">{chatMessage.date}</span>
                        </p>
                    ))}
                </div>
                <div className="input-area">
                    <input
                        type="text"
                        placeholder="Type your message"
                        value={message}
                        onChange={(e) => setMessage(e.target.value)}
                    />
                    <button onClick={sendMessage}>Send</button>
                </div>
            </div>
        </div>
    );
};
export default ChatRoom;
