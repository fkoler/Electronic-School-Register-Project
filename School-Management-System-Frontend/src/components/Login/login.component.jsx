import { useState } from 'react';
import PropTypes from 'prop-types';

import { getUsers } from '../../utils/api/api';

const Login = ({ onLogin }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const handleLogin = async (e) => {
        e.preventDefault();

        try {
            await getUsers(username, password);

            onLogin({ username, password });
        } catch (err) {
            setError('Login failed. Please try again.');
            console.error('Error during login:', err);
        }
    };

    return (
        <div>
            <form onSubmit={handleLogin}>
                <input
                    type='text'
                    placeholder='Username'
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                />

                <input
                    type='password'
                    placeholder='Password'
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />

                <button type='submit'>Login</button>
            </form>
            {error && <p style={{ color: 'red' }}>{error}</p>}
        </div>
    );
};

Login.propTypes = {
    onLogin: PropTypes.func.isRequired,
};

export default Login;
