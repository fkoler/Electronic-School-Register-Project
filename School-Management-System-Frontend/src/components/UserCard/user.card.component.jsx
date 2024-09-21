import { useState, useEffect } from 'react';

import PropTypes from 'prop-types';

import useAuthStore from '../../utils/auth/useAuthStore';
import { getUsersApi } from '../../services/api/api';

import SearchBar from '../SearchBar/searchbar.component';

const UserCard = () => {
    const [users, setUsers] = useState([]);
    const [filteredUsers, setFilteredUsers] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [error, setError] = useState(null);

    const email = useAuthStore((state) => state.user.email);
    const authPassword = useAuthStore((state) => state.user.password);
    const password = authPassword.replace('{noop}', '');

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const result = await getUsersApi(email, password);
                setUsers(result);
                setFilteredUsers(result);
            } catch (err) {
                setError('Failed to load users', err);
            }
        };

        fetchUsers();
    }, [email, password]);

    const normalizeString = (str) => str.trim().replace(/\s+/g, ' ');

    useEffect(() => {
        const normalizedSearchTerm = normalizeString(searchTerm.toLowerCase());

        setFilteredUsers(
            users.filter((user) =>
                normalizeString(
                    `${user.name} ${user.lastName} ${user.email}`.toLowerCase()
                ).includes(normalizedSearchTerm)
            )
        );
    }, [searchTerm, users]);

    if (error) {
        return <p>{error}</p>;
    }

    return (
        <div>
            <h2>Users Data:</h2>

            <SearchBar value={searchTerm} onChange={setSearchTerm} />

            <div style={{ display: 'flex', flexWrap: 'wrap' }}>
                {filteredUsers.map((user) => (
                    <div key={user.id} style={styles.card}>
                        <h3>{`${user.name} ${user.lastName}`}</h3>
                        <p>Email: {user.email}</p>
                        <p>Role: {user.role.name}</p>
                    </div>
                ))}
            </div>
        </div>
    );
};

UserCard.propTypes = {
    users: PropTypes.arrayOf(
        PropTypes.shape({
            id: PropTypes.number.isRequired,
            name: PropTypes.string.isRequired,
            lastName: PropTypes.string.isRequired,
            email: PropTypes.string.isRequired,
            role: PropTypes.shape({
                name: PropTypes.string.isRequired,
            }).isRequired,
        })
    ),
};

const styles = {
    card: {
        border: '1px solid #ccc',
        borderRadius: '8px',
        padding: '16px',
        margin: '16px',
        boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
        width: '200px',
        display: 'inline-block',
    },
};

export default UserCard;
