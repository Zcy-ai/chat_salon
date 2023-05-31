import { useLocation } from 'react-router-dom';
import '../chatRoom.css';
import {useEffect, useState} from "react";

import { Avatar, Button, Container, Grid, IconButton, List, ListItem, ListItemIcon, ListItemText, Paper, TextField, Typography, Box} from '@mui/material';
import { Send as SendIcon, Add as AddIcon , Delete as DeleteIcon} from '@mui/icons-material';

class MessageToSend {
    constructor(chatRoom, sender, content) {
        this.chatRoom = chatRoom;
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
    const location = useLocation();
    const state = location.state;
    let currentLogin = state.login; // email of user
    let currentFirstName = state.firstName; // firstName of user
    let currentLastName = state.lastName; // lastName of user
    const [currentChatRoomIndex, setCurrentChatRoomIndex] = useState(0); // current index of room in chatRoom
    const [currentChatRoomId, setCurrentChatRoomId] = useState(0); // current id of chatRoom at backend
    // const [currentMessageId, setCurrentMessageId] = useState(0);
    const [newChatName, setNewChatName] = useState('');
    const [isChatNameValid, setIsChatNameValid] = useState(true);
    const [message, setMessage] = useState(''); //发送消息的输入框
    const [socket, setSocket] = useState(null); //websocket connecté pour l'instant
    const [chatRoom, setChatRoom] = useState(state.chatRoomList); // list of chatRoom
    const [messageList,setMessageList] = useState([]); //当前聊天室的所有消息集合

    // 当用户点击切换聊天室时，我们更新currentChatRoomIndex和currentChatRoomId
    const handleChatClick = (roomIndex, roomId) => {
        setMessageList([]);
        setCurrentChatRoomIndex(roomIndex);
        setCurrentChatRoomId(roomId);
        console.log(currentChatRoomId);
        console.log(currentChatRoomIndex);
    };
    // const getChatID = (roomId) => {
    //     return chatRoom[roomId].id;
    // };
    const handleDeleteChat = (roomId) => {
        // TODO axios向后端发送删除chat的请求
        // TODO 更新chatRoom、currentChatRoomIndex和currentChatRoomId
        if (roomId === currentChatRoomIndex){ //删除当前的，默认切换到 0 号聊天室
            setCurrentChatRoomIndex(0);
        }
        // setChatRoom((prevChats) => {
        //     const updatedChats = prevChats.filter((chat) => chat.id !== chatId);
        //     return updatedChats;
        // });
    };
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
        // TODO axios访问后端接口创建群聊
        setChatRoom((prevChats) => [...prevChats, newChat]);
        setNewChatName('');
    };
    // 向后端发送消息
    const sendMessage = () => {
        if (message.trim() !== '') {
            //发送消息
            const newMessage = new MessageToSend(
                currentChatRoomId,
                currentLogin,
                message
            );
            socket.send(JSON.stringify(newMessage));
            setMessage(''); //发送出消息后清空消息输入框
        }
    };

    // 监控currentChatRoomId和currentLogin,当两者发生变化，更新websocket连接
    useEffect(() => {
        // 创建新的WebSocket连接
        const newSocket = new WebSocket(`ws://localhost:8080/chat/${currentLogin}/${currentChatRoomId}`);
        console.log(chatRoom);
        // 设置WebSocket事件处理程序
        newSocket.onopen = (event) => {
            console.log(newSocket);
        };
        newSocket.onmessage = (event) => {
            const receivedMessage = JSON.parse(event.data);
            const { sender, content } = receivedMessage;// TODO websocket接收ChatID，让消息只在一个群组里发送而不是广播
            const currentDate = new Date().toLocaleString(); // 添加日期
            const formattedMessage = `${sender}: ${content}`
            const newMessage = new MessageToPrint(
                messageList.length,// TODO index还是id
                sender,
                formattedMessage,
                currentDate,
            );
            // setChatRoom((prevChats) => {
            //     const updatedChats = [...prevChats];
            //     updatedChats[currentChatRoomIndex].messages.push(newMessage); //TODO index还是id
            //     return updatedChats;
            // });
            setMessageList(prevState => [...prevState, newMessage]);
        };
        newSocket.onclose = function (event) {
            console.log("Socket closed");
        };
        newSocket.onerror = function (err) {
            console.log('Socket encountered error: ', err.message, 'Closing socket');
            // setWebSocketReady(false);
            socket.close();
        };
        // 更新state中的socket
        setSocket(newSocket);

        // 用currentLogin和currentChat作为依赖项，任一变化都会重新运行effect
    }, [currentChatRoomId,currentLogin]);
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
                                    {chatRoom?.map((chat, index) => (
                                        <ListItem
                                            key={chat.id}
                                            button
                                            selected={chat.id === currentChatRoomId}
                                            onClick={() => handleChatClick(index, chat.id)}
                                            sx={{ borderRadius: 1, marginBottom: 1 }}
                                        >
                                            <ListItemIcon sx={{ minWidth: 32 }}>{<Avatar />}</ListItemIcon>
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
                                    {chatRoom[currentChatRoomIndex].name}
                                </Typography>
                            </Grid>
                            <Grid item sx={{ flexGrow: 1, overflowY: 'auto', padding: 2 }}>
                                {messageList?.map((message) => (
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
};
export default ChatRoom;
