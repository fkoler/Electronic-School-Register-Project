import { useState } from 'react';
import useAuthStore from '../../utils/auth/useAuthStore';
import { loginApi } from '../../services/api/api';

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const login = useAuthStore((state) => state.login);
    const isUserLogedIn = useAuthStore((state) => state.user);

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const users = await loginApi(email, password);
            const user = users.find((u) => u.email === email);

            if (!user) {
                console.error('User not found');
                setError('User not found');
                return;
            }

            login(user);

            if (user.role.name === 'ROLE_ADMIN') {
                window.location.href = '/admin';
            } else if (user.role.name === 'ROLE_TEACHER') {
                window.location.href = '/teacher';
            } else {
                window.location.href = '/';
            }
        } catch (error) {
            setError('Login failed: ' + error.message);
            console.error('Login failed:', error);
        }
    };

    return (
        <div>
            {isUserLogedIn === null && (
                <form onSubmit={handleSubmit}>
                    <div>
                        <label>Email:</label>
                        <input
                            type='email'
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />
                    </div>

                    <div>
                        <label>Password:</label>
                        <input
                            type='password'
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </div>

                    <button type='submit'>Login</button>
                    {error && <p style={{ color: 'red' }}>{error}</p>}
                </form>
            )}
        </div>
    );
};

export default Login;
