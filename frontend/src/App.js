import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Login from './componets/login';
import Register from './componets/register';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route exact path="/login" element={<Login/>} />
        <Route exact path="/register" element={<Register/>} />
        {/* <Route exact path="/admin" element={<Admin/>} /> */}
      </Routes>
    </BrowserRouter>
  );
}

export default App;
