import { BrowserRouter, Route, Routes, Navigate } from 'react-router-dom';
import Login from './componets/login';
import Register from './componets/register';
import Admin from './componets/admin';
import ChatRoom from "./componets/chatRoom";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} />
        <Route exact path="/login" element={<Login/>} />
        <Route exact path="/register" element={<Register/>} />
        <Route exact path="/admin" element={<Admin/>} />
        <Route exact path="/chatroom" element={<ChatRoom/>} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
