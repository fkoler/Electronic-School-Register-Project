import { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import useAuthStore from '../../utils/auth/useAuthStore';
import {
    getUsersApi,
    postUserApi,
    putUserApi,
    deleteUserApi,
} from '../../services/api/api';
import SearchBar from '../SearchBar/searchbar.component';

const UserCard = () => {
    const [users, setUsers] = useState([]);
    const [filteredUsers, setFilteredUsers] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [error, setError] = useState(null);
    const [showForm, setShowForm] = useState(false);
    const [newUser, setNewUser] = useState({
        name: '',
        lastName: '',
        password: '',
        email: '',
        role: '',
    });
    const [editingUser, setEditingUser] = useState(null);

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

    const handleAddUser = async () => {
        try {
            const result = await postUserApi(newUser, email, password);
            setUsers([...users, result]);
            setShowForm(false);
            setNewUser({
                name: '',
                lastName: '',
                password: '',
                email: '',
                role: '',
            });
        } catch (err) {
            setError('Failed to add user', err);
        }
    };

    const handleDeleteUser = async (userId) => {
        try {
            await deleteUserApi(userId, email, password);
            setUsers(users.filter((user) => user.id !== userId));
        } catch (err) {
            setError('Failed to delete user', err);
        } finally {
            setSearchTerm('');
        }
    };

    const handleEditUser = (user) => {
        setEditingUser(user);
        setShowForm(true);
        setSearchTerm('');
        setNewUser({
            name: user.name,
            lastName: user.lastName,
            password: '',
            email: user.email,
            role: user.role.id,
        });
    };

    const handleSaveEditUser = async () => {
        try {
            const updatedUser = await putUserApi(
                editingUser.id,
                newUser,
                email,
                password
            );
            setUsers((prevUsers) =>
                prevUsers.map((user) =>
                    user.id === editingUser.id
                        ? { ...user, ...newUser, role: updatedUser.role }
                        : user
                )
            );
            setEditingUser(null);
            setShowForm(false);
            setNewUser({
                name: '',
                lastName: '',
                password: '',
                email: '',
                role: '',
            });
        } catch (err) {
            setError('Failed to update user', err);
        }
    };

    const handleCancelEdit = () => {
        setEditingUser(null);
        setShowForm(false);
        setSearchTerm('');
        setNewUser({
            name: '',
            lastName: '',
            password: '',
            email: '',
            role: '',
        });
    };

    if (error) {
        return <p>{error}</p>;
    }

    return (
        <div>
            {!showForm && (
                <>
                    <h2>Users Data:</h2>
                    <SearchBar
                        value={searchTerm}
                        onChange={setSearchTerm}
                        placeholder='Search users...'
                    />{' '}
                    or:{' '}
                    <button onClick={() => setShowForm(true)}>
                        Add New User
                    </button>
                </>
            )}

            {showForm && (
                <div style={{ marginTop: '20px' }}>
                    <h3>{editingUser ? 'Edit User' : 'Add New User'}</h3>
                    <input
                        type='text'
                        placeholder='Name'
                        value={newUser.name}
                        onChange={(e) =>
                            setNewUser({ ...newUser, name: e.target.value })
                        }
                    />
                    <input
                        type='text'
                        placeholder='Last Name'
                        value={newUser.lastName}
                        onChange={(e) =>
                            setNewUser({ ...newUser, lastName: e.target.value })
                        }
                    />
                    <input
                        type='email'
                        placeholder='Email'
                        value={newUser.email}
                        onChange={(e) =>
                            setNewUser({ ...newUser, email: e.target.value })
                        }
                    />
                    <input
                        type='password'
                        placeholder='Password'
                        value={newUser.password}
                        onChange={(e) =>
                            setNewUser({ ...newUser, password: e.target.value })
                        }
                    />
                    <input
                        type='text'
                        placeholder='Role ID'
                        value={newUser.role}
                        onChange={(e) =>
                            setNewUser({ ...newUser, role: +e.target.value })
                        }
                    />

                    <button
                        onClick={
                            editingUser ? handleSaveEditUser : handleAddUser
                        }
                    >
                        {editingUser ? 'Save' : 'Submit'}
                    </button>
                    <button onClick={handleCancelEdit}>Cancel</button>
                </div>
            )}

            {!showForm && (
                <div style={{ display: 'flex', flexWrap: 'wrap' }}>
                    {filteredUsers.map((user) => (
                        <div key={user.id} style={styles.card}>
                            <h3>{`${user.name} ${user.lastName}`}</h3>
                            <p>Email: {user.email}</p>
                            <p>Role: {user.role.name}</p>
                            <button onClick={() => handleDeleteUser(user.id)}>
                                Delete
                            </button>{' '}
                            <button onClick={() => handleEditUser(user)}>
                                Edit
                            </button>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

UserCard.propTypes = {
    users: PropTypes.arrayOf(
        PropTypes.shape({
            id: PropTypes.number.isRequired,
            name: PropTypes.string.isRequired,
            lastName: PropTypes.string.isRequired,
            password: PropTypes.string.isRequired,
            email: PropTypes.string.isRequired,
            role: PropTypes.shape({
                id: PropTypes.number.isRequired,
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
        width: '300px',
        display: 'inline-block',
    },
};

export default UserCard;
