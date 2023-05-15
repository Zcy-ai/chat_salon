import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from 'react-router-dom';

const Register = () => {
    const [lastName, setLastName] = useState("");
    const [firstName, setFirstName] = useState("");
    const [login, setLogin] = useState("");
    const [password, setPassword] = useState("");
    const [gender, setGender] = useState("");
    const [admin, setAdmin] = useState(0);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();
        const formData = new FormData();
        formData.append('lastName', lastName);
        formData.append('firstName', firstName);
        formData.append('login', login);
        formData.append('admin', admin);
        formData.append('gender', gender);
        formData.append('password', password);

        try {
            const response = await axios.post("http://localhost:8080/register", formData);
            console.log(response);
            if (response.status === 200) {
                navigate('/login'); // TODO 更改重定向路由
            } else if (response.status === 404) {
                setError('Registration failed');
                navigate('/register');
            }
        } catch (error) {
            console.log(error);
        }
    };

    return (
        <div className="container">
            <h3>Register</h3>
            {error && <div className="alert alert-danger">{error}</div>}
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="lastName">Last Name:</label>
                    <input
                        type="text"
                        className="form-control"
                        id="lastName"
                        name="lastName"
                        value={lastName}
                        onChange={(e) => setLastName(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="firstName">First Name:</label>
                    <input
                        type="text"
                        className="form-control"
                        id="firstName"
                        name="firstName"
                        value={firstName}
                        onChange={(e) => setFirstName(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="login">Login:</label>
                    <input
                        type="text"
                        className="form-control"
                        id="login"
                        name="login"
                        value={login}
                        onChange={(e) => setLogin(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="password">Password:</label>
                    <input
                        type="password"
                        className="form-control"
                        id="password"
                        name="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="gender">Gender:</label>
                    <select
                        className="form-control"
                        id="gender"
                        name="gender"
                        value={gender}
                        onChange={(e) => setGender(e.target.value)}
                        required
                    >
                        <option value="">Choose...</option>
                        <option value="male">Male</option>
                        <option value="female">Female</option>
                    </select>
                </div>
                <div className="form-group">
                    <label htmlFor="admin">Admin:</label>
                    <select
                        className="form-control"
                        id="admin"
                        name="admin"
                        value={admin}
                        onChange={(e) => setAdmin(e.target.value)}
                        required
                    >
                        <option value="">Choose...</option>
                        <option value="1">Yes</option>
                        <option value="0">No</option>
                    </select>
                </div>
                <button type="submit" className="btn btn-primary">
                    Register
                </button>
            </form>
        </div>
    );
};

export default Register;
