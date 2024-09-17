import { useState, useEffect } from 'react';

import {
    BrowserRouter as Router,
    Route,
    Routes,
    Navigate,
} from 'react-router-dom';

import Login from './components/Login/login.component';
import AdminDashboard from './components/Admin/admin.component';

import { getUsersApi } from './services/api/api';

import './App.css';

const App = () => {
    const [user, setUser] = useState(null);
    const [credentials, setCredentials] = useState({
        username: '',
        password: '',
    });

    useEffect(() => {
        const storedUser = sessionStorage.getItem('user');
        const storedCredentials = sessionStorage.getItem('credentials');

        if (storedUser && storedCredentials) {
            setUser(JSON.parse(storedUser));
            setCredentials(JSON.parse(storedCredentials));
        }
    }, []);

    const handleLogin = async ({ username, password }) => {
        try {
            const user = await getUsersApi(username, password);
            setUser(user);
            setCredentials({ username, password });

            sessionStorage.setItem('user', JSON.stringify(user));
            sessionStorage.setItem(
                'credentials',
                JSON.stringify({ username, password })
            );
        } catch (err) {
            console.error('handleLogin error:', err);
        }
    };

    const handleLogout = () => {
        setUser(null);
        setCredentials({ username: '', password: '' });

        sessionStorage.removeItem('user');
        sessionStorage.removeItem('credentials');
    };

    return (
        <Router>
            <Routes>
                <Route
                    path='/'
                    element={
                        user ? (
                            <Navigate to='/admin' replace />
                        ) : (
                            <Login onLogin={handleLogin} />
                        )
                    }
                />

                <Route
                    path='/admin'
                    element={
                        user ? (
                            <div>
                                <div>
                                    <h2>{user[0]?.name}</h2>

                                    <button onClick={handleLogout}>
                                        Logout
                                    </button>
                                </div>

                                <AdminDashboard
                                    username={credentials.username}
                                    password={credentials.password}
                                />
                            </div>
                        ) : (
                            <Navigate to='/' replace />
                        )
                    }
                />
            </Routes>
        </Router>
    );
};

export default App;
