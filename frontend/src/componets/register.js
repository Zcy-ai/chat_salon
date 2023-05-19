import React, { useState} from "react";
import axios from "axios";
import { useNavigate } from 'react-router-dom';

const Register = () => {
    // const [lastName, setLastName] = useState("");
    // const [firstName, setFirstName] = useState("");
    // const [login, setLogin] = useState("");
    // const [password, setPassword] = useState("");
    // const [gender, setGender] = useState("");
    // const [admin, setAdmin] = useState(0);

    const [formData, setFormData] = useState({
        firstName:'',
        lastName:'',
        login: '',
        password: '',
        gender: '',
        admin: 0,
    });
    const [errors, setErrors] = useState('');
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevFormData) => ({
            ...prevFormData,
            [name]: value,
        }));
    };

    const navigate = useNavigate();
    const handleSubmit = async (event) => {
        event.preventDefault();
        if (validateForm()) {
            try {
                const userFormData = new FormData();
                userFormData.append('lastName', formData.lastName);
                userFormData.append('firstName', formData.firstName);
                userFormData.append('login', formData.login);
                userFormData.append('admin', formData.admin);
                userFormData.append('gender', formData.gender);
                userFormData.append('password', formData.password);
                const response = await axios.post("http://localhost:8080/register", userFormData);
                console.log(response);
                if (response.status === 200) {
                    navigate('/login'); // TODO 更改重定向路由
                } else if (response.status === 404) {
                    setErrors('Registration failed');
                    navigate('/register');
                }
            } catch (error) {
                console.log(error);
            }
        }
    };
    const validateForm = () => {
        let isValid = true;
        let newErrors = {};

        // 进行字段验证
        if (!formData.firstName) {
            newErrors.firstName = '请填写用户名';
            isValid = false;
        }

        if (!formData.lastName) {
            newErrors.lastName = '请填写用户名';
            isValid = false;
        }

        if (!formData.login) {
            newErrors.login = '请填写邮箱';
            isValid = false;
        }

        if (!formData.password) {
            newErrors.password = '请填写密码';
            isValid = false;
        }

        // if (formData.password !== formData.confirmPassword) {
        //     newErrors.confirmPassword = '密码不匹配';
        //     isValid = false;
        // }

        setErrors(newErrors);
        return isValid;
    };

    return (
        <div className="container">
            <h3>Register</h3>
            {/*{errors && <div className="alert alert-danger">{errors}</div>}*/}
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="firstName">First Name:</label>
                    <input
                        type="text"
                        className="form-control"
                        id="firstName"
                        name="firstName"
                        value={formData.firstName}
                        onChange={handleChange}
                        required
                    />
                    {errors.firstName && <span>{errors.firstName}</span>}
                </div>
                <div className="form-group">
                    <label htmlFor="lastName">Last Name:</label>
                    <input
                        type="text"
                        className="form-control"
                        id="lastName"
                        name="lastName"
                        value={formData.lastName}
                        onChange={handleChange}
                        required
                    />
                    {errors.lastName && <span>{errors.lastName}</span>}

                </div>
                <div className="form-group">
                    <label htmlFor="login">Login:</label>
                    <input
                        type="text"
                        className="form-control"
                        id="login"
                        name="login"
                        value={formData.login}
                        onChange={handleChange}
                        required
                    />
                    {errors.login && <span>{errors.login}</span>}

                </div>
                <div className="form-group">
                    <label htmlFor="password">Password:</label>
                    <input
                        type="password"
                        className="form-control"
                        id="password"
                        name="password"
                        value={formData.password}
                        onChange={handleChange}
                        required
                    />
                    {errors.password && <span>{errors.password}</span>}

                </div>
                <div className="form-group">
                    <label htmlFor="gender">Gender:</label>
                    <select
                        className="form-control"
                        id="gender"
                        name="gender"
                        value={formData.gender}
                        onChange={handleChange}
                        required
                    >
                        <option value="">Choose...</option>
                        <option value="male">Male</option>
                        <option value="female">Female</option>
                    </select>
                    {errors.gender && <span>{errors.gender}</span>}

                </div>
                <div className="form-group">
                    <label htmlFor="admin">Admin:</label>
                    <select
                        className="form-control"
                        id="admin"
                        name="admin"
                        value={formData.admin}
                        onChange={handleChange}
                        required
                    >
                        <option value="">Choose...</option>
                        <option value="1">Yes</option>
                        <option value="0">No</option>
                    </select>
                    {errors.admin && <span>{errors.admin}</span>}
                </div>
                <button type="submit" className="btn btn-primary">
                    Register
                </button>
            </form>
        </div>
    );
};

export default Register;
