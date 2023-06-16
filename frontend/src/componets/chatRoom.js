import { useLocation } from 'react-router-dom';
import axios from 'axios';
import {useEffect, useState} from "react";

import { Avatar, Button, Container, Grid, IconButton, List, ListItem, ListSubheader, ListItemIcon, ListItemText, Paper, TextField, Typography, Box} from '@mui/material';
import { Send as SendIcon, Add as AddIcon , Delete as DeleteIcon} from '@mui/icons-material';
import SpeakerNotesIcon from '@mui/icons-material/SpeakerNotes';
import PersonAddIcon from '@mui/icons-material/PersonAdd';


class Invitation {
    constructor(inviter, receiver, chatRoomID, chatRoomName, messageType) {
        this.inviter = inviter;
        this.receiver = receiver;
        this.chatRoomID = chatRoomID;
        this.chatRoomName = chatRoomName;
        this.messageType = messageType;
    }
}
class Message {
    constructor(chatRoom, sender, firstName, lastName, content, timestamp) {
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.firstName = firstName;
        this.lastName = lastName;
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
    const [currentChatRoomIndex, setCurrentChatRoomIndex] = useState(null); // current index of room in chatRoom
    const [currentChatRoomId, setCurrentChatRoomId] = useState(null); // current id of chatRoom at backend
    const [newChatName, setNewChatName] = useState('');
    const [isChatNameValid, setIsChatNameValid] = useState(true);
    const [message, setMessage] = useState(''); //发送消息的输入框
    const [socket, setSocket] = useState(null); //websocket connecté pour l'instant
    const [socketServ, setSocketServ] = useState(null); //websocket to server
    const [chatRoom, setChatRoom] = useState(state.chatRoomList); // list of chatRoom
    const [messageList,setMessageList] = useState([]); //当前聊天室的所有消息集合
    const [isAddingUser, setIsAddingUser] = useState(false);
    const [newUserLogin, setNewUserLogin] = useState('');
    const [myInvitation, setMyInvitation] = useState(null);
    const [invitationErr, setInvitationErr] = useState(null);
    const [token, setToken] = useState(state.token);

    const logout = () => {
        localStorage.removeItem('token');
        if (socket) {
            socket.close();
        }
        if (socketServ) {
            socketServ.close();
        }
        window.location.replace('/login');
    }
    const handleAddUserClick = () => {
        if (isAddingUser){
            setIsAddingUser(false);
            return;
        }
        setIsAddingUser(true);
    };

    const handleAddUserCancel = () => {
        setIsAddingUser(false);
        setNewUserLogin('');
    };

    const handleAddUserConfirm = () => {
        const trimmedLogin = newUserLogin.trim(); // 去除输入两边的空格
        if (trimmedLogin === currentLogin ) {
            setInvitationErr("Cannot invite yourself")
        }else if(trimmedLogin === ''){
            setInvitationErr("Empity field")
        }else{
            setInvitationErr(null)
            // 执行添加用户的逻辑
            const newInvitation = new Invitation(
                currentLogin,
                trimmedLogin,
                currentChatRoomId,
                chatRoom[currentChatRoomIndex].name,
                "INVITE"
            )
            socketServ.send(JSON.stringify(newInvitation));
            setIsAddingUser(false);
            setNewUserLogin('');
        }
    };
    const handleResponseInvite = (value) => {
        const newInvitation = new Invitation(
            myInvitation.receiver,
            myInvitation.inviter,
            myInvitation.chatRoomID,
            myInvitation.chatRoomName,
            value
        )
        socketServ.send(JSON.stringify(newInvitation));
        setMyInvitation(null);
        if (value === "CONFIRM"){
            const newChat = {
                id: myInvitation.chatRoomID,
                name: myInvitation.chatRoomName,
            };
            setChatRoom((prevChats) => [...prevChats, newChat]);
        }
    };

    // 当用户点击切换聊天室时，我们更新currentChatRoomIndex和currentChatRoomId
    const handleChatClick = (roomIndex, roomId) => {
        setIsAddingUser(false);
        // TODO 向后端请求聊天记录
        axios.get(`http://localhost:8080/chat_history/${roomId}/${token}`)
            .then((response) => {
                // const messageHistory = response.data;
                const messageHistory = response.data.map(JSON.parse);
                console.log(messageHistory);
                setMessageList(messageHistory);
                setCurrentChatRoomIndex(roomIndex);
                setCurrentChatRoomId(roomId);
            })
            .catch(error => {
                setMessageList([]);
                setCurrentChatRoomIndex(roomIndex);
                setCurrentChatRoomId(roomId);
            // 如果请求失败，可以在这里处理错误
                console.error('There has been a problem with your axios operation:', error);
            });
        // if (currentChatRoomIndex === roomIndex){
        //     setCurrentChatRoomIndex(null);
        //     setCurrentChatRoomId(null);
        //     setMessageList([]);
        //     return;
        // }
        // console.log(currentChatRoomId);
        // console.log(currentChatRoomIndex);
    };
    const handleDeleteChat = (roomId) => {
        // axios向后端发送删除chat的请求
        // TODO 更新chatRoom、currentChatRoomIndex和currentChatRoomId
        // if (roomId === currentChatRoomIndex){ //删除当前的，默认切换到 0 号聊天室
        //     setCurrentChatRoomIndex(0);
        // }
        axios.post(`http://localhost:8080/delete_chatroom/${currentLogin}/${roomId}/${token}`)
            .then((response) => {
                if (response.status === 200) {
                    // 后端删除成功后前端也进行删除
                    setChatRoom((prevChats) => {
                        const updatedChats = prevChats.filter((chat) => chat.id !== roomId);
                        return updatedChats;
                    });
                    // TODO 更新currentChatRoomIndex和currentChatRoomId
                    setCurrentChatRoomIndex(null);
                    setCurrentChatRoomId(null);
                    setMessageList([]);
                } else {
                    // TODO 请求失败的处理
                }
            }).catch((error) => {
            console.log(error);
        });
    };
    const handleCreateChat = () => {
        if (newChatName.trim() === '') {
            setIsChatNameValid(false);
            return;
        }
        axios.post(`http://localhost:8080/create_chatroom/${currentLogin}/${newChatName}/${token}`)
            .then((response) => {
                if (response.status === 200) {
                    console.log(response.data);
                    const newChat = {
                        ...response.data,
                    };
                    setChatRoom((prevChats) => [...prevChats, newChat]);
                } else {
                    // TODO 请求失败的处理
                }
            }).catch((error) => {
            console.log(error);
        });
        setNewChatName('');
    };
    // 向后端发送消息
    const sendMessage = () => {
        if (message.trim() !== '') {
            const currentDate = new Date().toLocaleString(); // 添加日期
            const formattedMessage = `${currentFirstName} ${currentLastName}: ${message}`
            //发送消息
            const newMessage = new Message (
                currentChatRoomId,
                currentLogin,
                currentFirstName,
                currentLastName,
                formattedMessage,
                currentDate,
            );
            socket.send(JSON.stringify(newMessage));
            setMessage(''); //发送出消息后清空消息输入框
        }
    };
    function stringToColor(string) {
        let hash = 0;
        let i;

        /* eslint-disable no-bitwise */
        for (i = 0; i < string.length; i += 1) {
            hash = string.charCodeAt(i) + ((hash << 5) - hash);
        }

        let color = '#';

        for (i = 0; i < 3; i += 1) {
            const value = (hash >> (i * 8)) & 0xff;
            color += `00${value.toString(16)}`.slice(-2);
        }
        /* eslint-enable no-bitwise */

        return color;
    }

    function stringAvatar(name) {
        return {
            sx: {
                bgcolor: stringToColor(name),
            },
            children: `${name.split(' ')[0][0]}${name.split(' ')[1][0]}`,
        };
    }
    // 监控currentChatRoomId和currentLogin,当两者发生变化，更新websocket连接
    useEffect(() => {
        if (currentChatRoomId === null){
            setSocket(null);
        } else {
            // 创建新的WebSocket连接
            const newSocket = new WebSocket(`ws://localhost:8080/chat/${currentLogin}/${currentChatRoomId}/${token}`);
            console.log(chatRoom);
            // 设置WebSocket事件处理程序
            newSocket.onopen = (event) => {
                console.log(newSocket);
            };
            newSocket.onmessage = (event) => {
                const receivedMessage = JSON.parse(event.data);
                const {sender, firstName, lastName, content, timestamp} = receivedMessage;
                // const currentDate = new Date().toLocaleString(); // 添加日期
                // const formattedMessage = `${firstName} ${lastName}: ${content}`
                const newMessage = new Message(
                    currentChatRoomId,
                    sender,
                    firstName,
                    lastName,
                    content,
                    timestamp,
                );
                setMessageList(prevState => {
                    const updatedList = [...prevState, newMessage];
                    return updatedList;
                });
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
        }
    }, [currentChatRoomId,currentLogin]);
    useEffect(()=>{
        const ws2Server = new WebSocket(`ws://localhost:8080/contact/${currentLogin}/${token}`);
        ws2Server.onopen = (event) => {
            console.log(ws2Server);
        };
        ws2Server.onmessage = (event) => {
            const receivedMessage = JSON.parse(event.data);
            const { inviter, receiver, chatRoomID, chatRoomName, messageType } = receivedMessage;
            console.log("Message form server");
            console.log(receivedMessage);
            const newInvitation = new Invitation(
                inviter,
                receiver,
                chatRoomID,
                chatRoomName,
                messageType
            )
            if (messageType === "INVITE") {
                setMyInvitation(newInvitation);
            }else if (messageType === "DELETEROOM") {
                setCurrentChatRoomIndex(null);
                setCurrentChatRoomId(null);
                setMessageList([]);
                setChatRoom((prevChats) => {
                    const updatedChats = prevChats.filter((chat) => chat.id !== chatRoomID);
                    return updatedChats;
                });
            }else if(messageType === "Inviter is already in the chatRoom" || messageType ==="Inviter doesn't exist"){
                setIsAddingUser(true);
                setInvitationErr(messageType);
            }
        };
        ws2Server.onclose = function (event) {
            console.log("Socket closed");
        };
        ws2Server.onerror = function (err) {
            console.log('Socket encountered error: ', err.message, 'Closing socket');
            // setWebSocketReady(false);
            ws2Server.close();
        };
        setSocketServ(ws2Server);
    },[currentLogin]);
    return (
        <Container maxWidth="lg" sx={{ marginTop: 4, height: 'calc(100vh - 64px)' }}>
            <Grid container spacing={2} sx={{ height: '100%' }}>
                <Grid item xs={4} sx={{ height: '100%' }}>
                    <Paper elevation={3} sx={{ height: '100%'}}>
                        <Grid container direction="column" justifyContent="space-between" sx={{ height: '100%', padding: 2, position: 'relative'  }}>
                            <Grid item sx={{ position: 'absolute', top: 0, left: 0, right: 0, margin: '10px' }}>
                                <Grid container alignItems="center" spacing={2}>
                                    <Grid item>
                                        <Avatar {...stringAvatar(currentFirstName+' '+currentLastName)} />
                                    </Grid>
                                    <Grid item>
                                        <Typography variant="h6">{currentFirstName} {currentLastName}</Typography>
                                    </Grid>
                                    <Grid item>
                                        <Button variant="contained" color="primary" onClick={logout}>
                                            Logout
                                        </Button>
                                    </Grid>
                                </Grid>
                            </Grid>
                            <Grid item sx={{ flexGrow: 1 }}>
                                <div style={{ height: '60px' }}></div>
                                <List component="nav" sx={{ flexGrow: 1, maxHeight: 'calc(100% - 160px)', overflowY: 'auto' }}>
                                    <ListSubheader sx={{ backgroundColor: '#f3e5f5', borderBottom: '1px solid #000' }}>Chef</ListSubheader>
                                    {chatRoom?.map((chat, index) => {
                                        const isChef = chat.chef && chat.chef.login === currentLogin;

                                        if (isChef) {
                                            return (
                                                <ListItem
                                                    key={chat.id}
                                                    button
                                                    selected={chat.id === currentChatRoomId}
                                                    onClick={() => handleChatClick(index, chat.id)}
                                                    sx={{
                                                        borderRadius: 1,
                                                        marginBottom: 1,
                                                        backgroundColor: '#f3e5f5'
                                                    }}
                                                >
                                                    <ListItemIcon sx={{ minWidth: 32 }}>{<SpeakerNotesIcon />}</ListItemIcon>
                                                    <ListItemText primary={chat.name} />
                                                    <IconButton edge="end" onClick={() => handleDeleteChat(chat.id)}>
                                                        <DeleteIcon />
                                                    </IconButton>
                                                </ListItem>
                                            );
                                        }

                                        return null; // 不在 "Chef" 分类中的聊天室项将不会显示
                                    })}
                                    <ListSubheader sx={{ backgroundColor: '#e1f5fe', borderBottom: '1px solid #000'}}>Member</ListSubheader>
                                    {chatRoom?.map((chat, index) => {
                                        const isChef = chat.chef && chat.chef.login === currentLogin;

                                        if (!isChef) {
                                            return (
                                                <ListItem
                                                    key={chat.id}
                                                    button
                                                    selected={chat.id === currentChatRoomId}
                                                    onClick={() => handleChatClick(index, chat.id)}
                                                    sx={{
                                                        borderRadius: 1,
                                                        marginBottom: 1,
                                                        backgroundColor: '#e1f5fe'
                                                    }}
                                                >
                                                    <ListItemIcon sx={{ minWidth: 32 }}>{<SpeakerNotesIcon />}</ListItemIcon>
                                                    <ListItemText primary={chat.name} />
                                                    <IconButton edge="end" onClick={() => handleDeleteChat(chat.id)}>
                                                        <DeleteIcon />
                                                    </IconButton>
                                                </ListItem>
                                            );
                                        }

                                        return null; // 不在 "Member" 分类中的聊天室项将不会显示
                                    })}
                                </List>
                            </Grid>

                            <Grid item sx={{ position: 'absolute', bottom: 0, left: 0, right: 0, margin: '10px' }}>
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
                {/*条件渲染*/}
                {(currentChatRoomIndex!== null) && (
                    <Grid item xs={8} sx={{ height: '100%' }}>
                        <Paper elevation={3} sx={{ height: '100%' }}>
                            <Grid container direction="column" justifyContent="space-between" sx={{ height: '100%', padding: 2, position: 'relative' }}>
                                <Grid item sx={{ position: 'absolute', top: 0, left: 0, right: 0, margin: '10px' }}>
                                    <Typography variant="h6" sx={{ padding: 2, position: 'sticky', bottom: 0, textAlign: 'center', backgroundColor: '#f5f5f5', color: '#333333', fontWeight: 'bold' }}>
                                        {chatRoom[currentChatRoomIndex].name}
                                        <IconButton sx={{ position: 'absolute', right: '5px' }} onClick={handleAddUserClick}>
                                            <PersonAddIcon sx={{ color: 'gray' }} />
                                        </IconButton>
                                    </Typography>
                                </Grid>
                                <Grid item sx={{ flexGrow: 1, maxHeight: 'calc(100% - 80px)', overflowY: 'auto', paddingTop: '70px' }}>
                                    {messageList?.map((message, index) => (
                                        <Box
                                            key={index}
                                            sx={{
                                                display: 'flex',
                                                justifyContent: message.firstName + ' ' + message.lastName === `${currentFirstName} ${currentLastName}` ? 'flex-end' : 'flex-start',
                                                mb: 1,
                                            }}
                                        >
                                            <Box
                                                sx={{
                                                    backgroundColor: message.firstName + ' ' + message.lastName === `${currentFirstName} ${currentLastName}` ? '#f3e5f5' : '#e1f5fe',
                                                    color: message.firstName + ' ' + message.lastName === `${currentFirstName} ${currentLastName}` ? '#000000' : '#000000',
                                                    padding: '8px 12px',
                                                    borderRadius: '8px',
                                                    maxWidth: '300px',
                                                    width: 'fit-content',
                                                    overflow: 'hidden',
                                                    textOverflow: 'ellipsis',
                                                    wordWrap: 'break-word',
                                                }}
                                            >
                                                <Typography variant="caption" sx={{ color: 'gray', fontSize: '0.75rem', textAlign: 'right' }}>
                                                    {message.timestamp}
                                                </Typography>
                                                <Typography variant="body1" sx={{ whiteSpace: 'pre-wrap' }}>{message.content}</Typography>
                                            </Box>
                                        </Box>
                                    ))}
                                </Grid>
                                <Grid item sx={{ position: 'absolute', bottom: 0, left: 0, right: 0, margin: '10px' }}>
                                    <Grid container spacing={2} alignItems="center" sx={{ padding: 2, position: 'sticky', bottom: 0 }}>
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
                )}
            </Grid>
            {/* 添加用户输入框 */}
            {isAddingUser && (
                <Box
                    sx={{
                        position: 'fixed',
                        top: '50%',
                        left: '50%',
                        transform: 'translate(-50%, -50%)',
                        backgroundColor: '#ffffff',
                        padding: 4,
                        borderRadius: '8px',
                        boxShadow: '0px 2px 8px rgba(0, 0, 0, 0.15)',
                    }}
                >
                    <Typography variant="h6">Add User</Typography>
                    <TextField
                        value={newUserLogin}
                        onChange={(e) => {
                            setNewUserLogin(e.target.value)
                        }}
                        label="User Login"
                        fullWidth
                        variant="outlined"
                        error={invitationErr !== null}
                        helperText={invitationErr}

                        sx={{ marginTop: 2 }}
                        onKeyDown={(e) => {
                            if (e.key === 'Enter' && !e.shiftKey) {
                                e.preventDefault();
                                handleAddUserConfirm();
                            }
                        }}
                    />
                    <Box sx={{ display: 'flex', justifyContent: 'center', marginTop: 2 }}>
                        <Button variant="outlined" color="primary" onClick={handleAddUserConfirm} sx={{ marginTop: 2}}>
                            Confirm
                        </Button>
                        <div style={{ width:'30px' }}></div>
                        <Button variant="outlined" onClick={handleAddUserCancel} sx={{ marginTop: 2 }}>
                            Cancel
                        </Button>
                    </Box>
                </Box>
            )}
            {/* 接受邀请 */}
            {myInvitation && (
                <Box
                    sx={{
                        position: 'fixed',
                        top: '50%',
                        left: '50%',
                        transform: 'translate(-50%, -50%)',
                        backgroundColor: '#ffffff',
                        padding: 4,
                        borderRadius: '8px',
                        boxShadow: '0px 2px 8px rgba(0, 0, 0, 0.15)',
                    }}
                >
                    <Typography variant="h6">Invitation from {myInvitation.inviter} to {myInvitation.chatRoomName} </Typography>
                    <Box sx={{ display: 'flex', justifyContent: 'center', marginTop: 2 }}>
                        <Button variant="outlined" color="primary" onClick={() => handleResponseInvite("CONFIRM")} sx={{ marginTop: 2}}>
                            Confirm
                        </Button>
                        <div style={{ width:'30px' }}></div>
                        <Button variant="outlined" onClick={() => handleResponseInvite("REFUSE")} sx={{ marginTop: 2 }}>
                            Refuse
                        </Button>
                    </Box>
                </Box>
            )}
        </Container>
    );
};
export default ChatRoom;
