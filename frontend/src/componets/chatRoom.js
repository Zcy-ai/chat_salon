import { useLocation } from 'react-router-dom';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../chatRoom.css';
import img from '../images/user.svg';
function ChatRoom() {
    let location = useLocation();
    let userList = location.state.userList;
    let currentFirstName = location.state.firstName;
    let currentLastName = location.state.lastName;
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
                    {/* Display chat messages */}
                </div>
                <div className="input-area">
                    <input type="text" placeholder="Type your message" />
                    <button>Send</button>
                </div>
            </div>
        </div>
    );
};

export default ChatRoom;
