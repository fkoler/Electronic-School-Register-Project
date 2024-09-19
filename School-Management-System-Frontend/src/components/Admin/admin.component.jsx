import { useState, useCallback } from 'react';
import useAuthStore from '../../features/auth/useAuthStore';
import {
    getUsersApi,
    getClassesApi,
    getSubjectsApi,
} from '../../services/api/api';

const AdminDashboard = () => {
    const [data, setData] = useState(null);
    const [error, setError] = useState(null);
    const [type, setType] = useState('');

    const email = useAuthStore((state) => state.user.email);
    const authPassword = useAuthStore((state) => state.user.password);
    const password = authPassword.replace('{noop}', '');

    const fetchData = useCallback(
        async (type) => {
            setError(null);
            setType(type);
            try {
                let result;
                if (type === 'users') {
                    result = await getUsersApi(email, password);
                } else if (type === 'classes') {
                    result = await getClassesApi(email, password);
                } else if (type === 'subjects') {
                    result = await getSubjectsApi(email, password);
                }
                setData(result);
            } catch (err) {
                setError(`Failed to load ${type}`, err);
            }
        },
        [email, password]
    );

    return (
        <div>
            <h1>Welcome Admin</h1>
            <div>
                <button onClick={() => fetchData('users')}>Users</button>{' '}
                <button onClick={() => fetchData('classes')}>Classes</button>{' '}
                <button onClick={() => fetchData('subjects')}>Subjects</button>
            </div>
            {error && <p>{error}</p>}
            {data && (
                <div>
                    <h2>
                        {type.charAt(0).toUpperCase() + type.slice(1)} Data:
                    </h2>
                    <pre>{JSON.stringify(data, null, 2)}</pre>
                </div>
            )}
        </div>
    );
};

export default AdminDashboard;
