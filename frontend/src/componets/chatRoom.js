import { useLocation } from 'react-router-dom';
import '../chatRoom.css';
import {useEffect, useState} from "react";

import { Avatar, Button, Container, Grid, IconButton, List, ListItem, ListItemIcon, ListItemText, Paper, TextField, Typography, Box} from '@mui/material';
import { Send as SendIcon, Add as AddIcon , Delete as DeleteIcon} from '@mui/icons-material';

class MessageToSend {
    constructor(id, sender, content) {
        this.id = id;
        this.sender = sender;
        this.content = content;
    }
}

class MessageToPrint {
    constructor(id, sender, content, timestamp) {
        this.id = id;
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
    }
}
function ChatRoom() {
    let location = useLocation();
    let userList = location.state.userList;
    let currentFirstName = location.state.firstName;
    let currentLastName = location.state.lastName;
    const [currentChat, setCurrentChat] = useState(0);
    const [newChatName, setNewChatName] = useState('');
    const [isChatNameValid, setIsChatNameValid] = useState(true);
    const [message, setMessage] = useState('');
    const [chatRoom, setChatRoom] = useState([
        {
            id: 0,
            name: 'Chat Room 0',
            icon: <Avatar />,
            messages: [
                {id: 0, sender: 'Chat Room 0', content: 'Welcome' },
            ],
            timestamp:new Date().toLocaleString(),
        }
    ]);
    const handleChatClick = (chatId) => {
        setCurrentChat(chatId);
    };
    const handleDeleteChat = (chatId) => {
        if (chatId == 0){ //不删除 0 号聊天室
            return
        }
        if (chatId == currentChat){ //删除当前的，默认切换到 0 号聊天室
            setCurrentChat(0);
        }
        // setChatRoom((prevChats) => { // TODO 删除的逻辑还是有问题
        //     const updatedChats = prevChats.filter((chat) => chat.id !== chatId);
        //     return updatedChats;
        // });
    };
    const socket = new WebSocket("ws://localhost:8080/chat/"+currentLastName+"_"+currentFirstName+"/"+chatRoom[currentChat].id);
    // 处理消息发送
    const sendMessage = () => {
        if (message.trim() !== '') {
            // console.log(chatRoom)
            //发送消息
            const newMessage = new MessageToSend(
                chatRoom[currentChat].messages.length,
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
            const { sender, content } = receivedMessage;// TODO websocket接收ChatID，让消息只在一个群组里发送而不是广播
            const currentDate = new Date().toLocaleString(); // 添加日期
            const formattedMessage = `${sender}: ${content}`
            const newMessage = new MessageToPrint(
                chatRoom[currentChat].messages.length, //TODO 此处要修改，把currentChat改成ChatID
                sender,
                formattedMessage,
                currentDate,
            );
            console.log("ID: ",newMessage.id)
            setChatRoom((prevChats) => {
                const updatedChats = [...prevChats];
                updatedChats[currentChat].messages.push(newMessage);
                return updatedChats;
            });
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
    const handleCreateChat = () => {
        if (newChatName.trim() === '') {
            setIsChatNameValid(false);
            return;
        }
        const newChat = {
            id: chatRoom.length,
            name: newChatName,
            icon: <Avatar />,
            messages: [
                { id: 0, sender: newChatName, content: 'Welcome' },
            ],
        };
        setChatRoom((prevChats) => [...prevChats, newChat]);
        setNewChatName('');
    };
    return (
        <Container maxWidth="lg" sx={{ marginTop: 4, height: 'calc(100vh - 64px)' }}>
            <Grid container spacing={2} sx={{ height: '100%' }}>
                <Grid item xs={4} sx={{ height: '100%' }}>
                    <Paper elevation={3} sx={{ height: '100%' }}>
                        <Grid container direction="column" justifyContent="space-between" sx={{ height: '100%', padding: 2 }}>
                            <Grid item>
                                <Grid container alignItems="center" spacing={2}>
                                    <Grid item>
                                        <Avatar />
                                    </Grid>
                                    <Grid item>
                                        <Typography variant="h6">{currentFirstName} {currentLastName}</Typography>
                                    </Grid>
                                </Grid>
                            </Grid>
                            <Grid item sx={{ flexGrow: 1 }}>
                                <List component="nav" sx={{ overflow: 'auto' }}>
                                    {chatRoom.map((chat) => (
                                        <ListItem
                                            key={chat.id}
                                            button
                                            selected={chat.id === currentChat}
                                            onClick={() => handleChatClick(chat.id)}
                                            sx={{ borderRadius: 1, marginBottom: 1 }}
                                        >
                                            <ListItemIcon sx={{ minWidth: 32 }}>{chat.icon}</ListItemIcon>
                                            <ListItemText primary={chat.name} />
                                            <IconButton edge="end" onClick={() => handleDeleteChat(chat.id)}>
                                                <DeleteIcon />
                                            </IconButton>
                                        </ListItem>
                                    ))}
                                </List>
                            </Grid>
                            <Grid item>
                                <Button startIcon={<AddIcon />} fullWidth variant="contained" color="primary" onClick={handleCreateChat}>
                                    Create Chat
                                </Button>
                                <TextField
                                    value={newChatName}
                                    onChange={(e) => {
                                        setNewChatName(e.target.value);
                                        setIsChatNameValid(true);
                                    }}
                                    label="Chat Room Name"
                                    fullWidth
                                    variant="outlined"
                                    error={!isChatNameValid}
                                    helperText={!isChatNameValid && 'Chat Room Name cannot be empty'}
                                    sx={{ marginTop: 2 }}
                                    onKeyDown={(e) => {
                                        if (e.key === 'Enter' && !e.shiftKey) {
                                            e.preventDefault();
                                            handleCreateChat();
                                        }
                                    }}
                                />
                            </Grid>
                        </Grid>
                    </Paper>
                </Grid>
                <Grid item xs={8} sx={{ height: '100%' }}>
                    <Paper elevation={3} sx={{ height: '100%' }}>
                        <Grid container direction="column" justifyContent="space-between" sx={{ height: '100%', padding: 2 }}>
                            <Grid item>
                                <Typography variant="h6" sx={{ padding: 2 }}>
                                    {chatRoom[currentChat].name}
                                </Typography>
                            </Grid>
                            <Grid item sx={{ flexGrow: 1, overflowY: 'auto', padding: 2 }}>
                                {chatRoom[currentChat].messages.map((message) => (
                                    <Box
                                        key={message.id}
                                        sx={{
                                            display: 'flex',
                                            justifyContent: message.sender === `${currentFirstName} ${currentLastName}` ? 'flex-start' : 'flex-end',
                                            mb: 1,
                                        }}
                                    >
                                        <Box
                                            sx={{
                                                backgroundColor: message.sender === `${currentFirstName} ${currentLastName}` ? '#e1f5fe' : '#f3e5f5',
                                                color: message.sender === `${currentFirstName} ${currentLastName}` ? '#000000' : '#000000',
                                                padding: '8px 12px',
                                                borderRadius: '8px',
                                                maxWidth: '70%',
                                                width: 'fit-content',
                                            }}
                                        >
                                            <Typography variant="caption" sx={{ color: 'gray', fontSize: '0.75rem', textAlign: 'right' }}>
                                                {message.timestamp}
                                            </Typography>
                                            <Typography variant="body1">{message.content}</Typography>
                                        </Box>
                                    </Box>
                                ))}
                            </Grid>
                            <Grid item>
                                <Grid container spacing={2} alignItems="center" sx={{ padding: 2 }}>
                                    <Grid item xs>
                                        <TextField
                                            value={message}
                                            onChange={(e) => setMessage(e.target.value)}
                                            label="Message"
                                            fullWidth
                                            variant="outlined"
                                            onKeyDown={(e) => {
                                                if (e.key === 'Enter' && !e.shiftKey) {
                                                    e.preventDefault();
                                                    sendMessage();
                                                }
                                            }}
                                        />
                                    </Grid>
                                    <Grid item>
                                        <IconButton onClick={sendMessage} disabled={!message.trim()}>
                                            <SendIcon />
                                        </IconButton>
                                    </Grid>
                                </Grid>
                            </Grid>
                        </Grid>
                    </Paper>
                </Grid>
            </Grid>
        </Container>
    );

    // return (
    //     <div className="container-chat">
    //         <div className="user-information">
    //             <div className="user-profile">
    //                 <img className="avatar" src={img} alt="Avatar" />
    //                 <div className="user-current">{currentFirstName} {currentLastName}</div>
    //                 <button className="logout">Logout</button>
    //             </div>
    //             <div className="online-users">
    //                 {/* 现在是全部用户而不是在线用户 */}
    //                 <h2>Online Users</h2>
    //                 {userList.map(user => (
    //                     <div key={user.id}>
    //                     <img className="avatar" src={img} alt="Avatar" />
    //                     <p className="name">{user.firstName} {user.lastName}</p>
    //                     </div>
    //                 ))}
    //             </div>
    //         </div>
    //         <div className="chat-room">
    //             <h2>Chat Room</h2>
    //             <div className="message-area">
    //                 {chatMessages.map((chatMessage, index) => (
    //                     <p key={index}>
    //                         <span className="message-content">{chatMessage.message}</span>
    //                         <span className="message-date">{chatMessage.date}</span>
    //                     </p>
    //                 ))}
    //             </div>
    //             <div className="input-area">
    //                 <input
    //                     type="text"
    //                     placeholder="Type your message"
    //                     value={message}
    //                     onChange={(e) => setMessage(e.target.value)}
    //                 />
    //                 <button onClick={sendMessage}>Send</button>
    //             </div>
    //         </div>
    //     </div>
    // );
};
export default ChatRoom;
