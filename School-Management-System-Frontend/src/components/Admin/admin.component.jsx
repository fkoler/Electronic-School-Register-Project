import { useState } from 'react';

import PropTypes from 'prop-types';

import {
    getUsersApi,
    getClassesApi,
    getSubjectsApi,
} from '../../services/api/api';

const AdminDashboard = ({ username, password }) => {
    const [data, setData] = useState(null);
    const [error, setError] = useState(null);
    const [type, setType] = useState('');

    const fetchData = async (type) => {
        setError(null);
        setType(type);
        try {
            let result;
            if (type === 'users') {
                result = await getUsersApi(username, password);
            } else if (type === 'classes') {
                result = await getClassesApi(username, password);
            } else if (type === 'subjects') {
                result = await getSubjectsApi(username, password);
            }
            setData(result);
        } catch (err) {
            setError(`Failed to load ${type}`, err);
        }
    };

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

AdminDashboard.propTypes = {
    username: PropTypes.string.isRequired,
    password: PropTypes.string.isRequired,
};

export default AdminDashboard;
