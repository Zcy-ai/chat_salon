import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Login() {
    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    // const [userList, setUserList] = useState([]);
    const navigate = useNavigate();

    const handleLoginChange = (event) => {
        setLogin(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        const formData = new FormData();
        // let socket = null;
        formData.append('login', login);
        formData.append('password', password);

        axios.post('http://localhost:8080/login', formData)
            .then((response) => {
                if (response.status === 200) {
                    console.log(response);
                    console.log(response.data.userList);
                    // socket = new WebSocket("ws://localhost:8080/chat/"+login);
                    // // 如果websocket创建失败
                    // socket.onerror = function() {
                    //     setError('Websocket failed');
                    //     return;
                    // };
                    navigate("/chatRoom", { state: { firstName:response.data.firstName, lastName:response.data.lastName, userList: response.data.userList} });
                } else {
                    setError('Authentication failed');
                }
            })
            .catch((error) => {
                console.log(error);
                setError('Authentication failed');
            });
    };

    return (
        <div className="container">
            <div className="row justify-content-center mt-5">
                <div className="col-md-6">
                    <h3 className="text-center mb-4">Login</h3>
                    {error && <div className="alert alert-danger">{error}</div>}
                    <form onSubmit={handleSubmit}>
                        <div className="form-group">
                            <label htmlFor="login">Login:</label>
                            <input type="text" className="form-control" id="login" name="login" required onChange={handleLoginChange} />
                        </div>
                        <div className="form-group">
                            <label htmlFor="password">Password:</label>
                            <input type="password" className="form-control" id="password" name="password" required onChange={handlePasswordChange} />
                        </div>
                        <button type="submit" className="btn btn-primary btn-block">Login</button>
                        <div className="text-center mt-3">
                            <a href="/register" className="text-muted">Register?</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default Login;
