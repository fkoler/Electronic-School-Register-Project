import { useState } from 'react';
import Login from './components/Login/login.component';
import { getUsers } from './utils/api/api';

import './App.css';

const App = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [users, setUsers] = useState([]);

    const handleLogin = async ({ username, password }) => {
        try {
            const fetchedUsers = await getUsers(username, password);
            setUsers(fetchedUsers);
            setIsLoggedIn(true);
        } catch (err) {
            console.error('Failed to fetch users:', err);
        }
    };

    return (
        <div>
            {!isLoggedIn ? (
                <Login onLogin={handleLogin} />
            ) : (
                <div>
                    <h2>List of users:</h2>
                    <ul>
                        {users.map((user) => (
                            <li key={user.id}>
                                {user.name} {user.lastName} - {user.email}{' '}
                                (Role: {user.role.name})
                            </li>
                        ))}
                    </ul>
                </div>
            )}
        </div>
    );
};

export default App;
