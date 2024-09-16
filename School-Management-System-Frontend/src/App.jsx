import { useState, useEffect } from 'react';
import Login from './components/Login/login.component';

import { getUsersApi } from './services/api/api';

import './App.css';

const App = () => {
    const [user, setUser] = useState(null);

    useEffect(() => {
        const storedUser = sessionStorage.getItem('user');

        if (storedUser) {
            setUser(JSON.parse(storedUser));
        }
    }, []);

    const handleLogin = async ({ username, password }) => {
        try {
            const user = await getUsersApi(username, password);

            setUser(user);

            sessionStorage.setItem('user', JSON.stringify(user));
        } catch (err) {
            console.error('handleLogin error:', err);
        }
    };

    const handleLogout = () => {
        setUser(null);

        sessionStorage.removeItem('user');
    };

    return (
        <div>
            {user ? (
                <div>
                    <h2>Welcome {user[0]?.name}</h2>

                    <button onClick={handleLogout}>Logout</button>
                </div>
            ) : (
                <Login onLogin={handleLogin} />
            )}
        </div>
    );
};

export default App;
