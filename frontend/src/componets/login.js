import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';

function Login() {
    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    // const [userList, setUserList] = useState([]);
    const navigate = useNavigate();
    const defaultTheme = createTheme();

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
    function Copyright(props) {
        return (
            <Typography variant="body2" color="text.secondary" align="center" {...props}>
                {'Copyright © '}
                <Link color="inherit" href="https://mui.com/">
                    Your Website
                </Link>{' '}
                {new Date().getFullYear()}
                {'.'}
            </Typography>
        );
    }
    // return (
    //     <div className="container">
    //         <div className="row justify-content-center mt-5">
    //             <div className="col-md-6">
    //                 <h3 className="text-center mb-4">Login</h3>
    //                 {error && <div className="alert alert-danger">{error}</div>}
    //                 <form onSubmit={handleSubmit}>
    //                     <div className="form-group">
    //                         <label htmlFor="login">Login:</label>
    //                         <input type="text" className="form-control" id="login" name="login" required onChange={handleLoginChange} />
    //                     </div>
    //                     <div className="form-group">
    //                         <label htmlFor="password">Password:</label>
    //                         <input type="password" className="form-control" id="password" name="password" required onChange={handlePasswordChange} />
    //                     </div>
    //                     <button type="submit" className="btn btn-primary btn-block">Login</button>
    //                     <div className="text-center mt-3">
    //                         <a href="/register" className="text-muted">Register?</a>
    //                     </div>
    //                 </form>
    //             </div>
    //         </div>
    //     </div>
    // );
    return (
        <ThemeProvider theme={defaultTheme}>
            <Container component="main" maxWidth="xs">
                <CssBaseline />
                <Box
                    sx={{
                        marginTop: 8,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                    }}
                >
                    <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
                        <LockOutlinedIcon />
                    </Avatar>
                    <Typography component="h1" variant="h5">
                        Sign in
                    </Typography>
                    <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            id="login"
                            label="Email Address"
                            name="login"
                            autoComplete="email"
                            autoFocus
                            required
                            onChange={handleLoginChange}
                        />
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            name="password"
                            label="Password"
                            type="password"
                            id="password"
                            autoComplete="current-password"
                            required
                            onChange={handlePasswordChange}
                        />
                        <FormControlLabel
                            control={<Checkbox value="remember" color="primary" />}
                            label="Remember me"
                        />
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            sx={{ mt: 3, mb: 2 }}
                        >
                            Sign In
                        </Button>
                        <Grid container>
                            <Grid item xs>
                                <Link href="#" variant="body2">
                                    Forgot password?
                                </Link>
                            </Grid>
                            <Grid item>
                                <Link href="/register" variant="body2">
                                    {"Don't have an account? Sign Up"}
                                </Link>
                            </Grid>
                        </Grid>
                    </Box>
                </Box>
                <Copyright sx={{ mt: 8, mb: 4 }} />
            </Container>
        </ThemeProvider>
    );
}

export default Login;
