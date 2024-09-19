import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

import Header from './components/Header/header.component';
import Home from './components/Home/home.component';
import Login from './components/Login/login.component';
import Admin from './components/Admin/admin.component';
import Teacher from './components/Teacher/teacher.component';

import './App.css';

function App() {
    return (
        <>
            <Router>
                <Header />
                <Routes>
                    <Route path='/' element={<Home />} />
                    <Route path='/login' element={<Login />} />
                    <Route path='/admin' element={<Admin />} />
                    <Route path='/teacher' element={<Teacher />} />
                </Routes>
            </Router>
        </>
    );
}

export default App;
