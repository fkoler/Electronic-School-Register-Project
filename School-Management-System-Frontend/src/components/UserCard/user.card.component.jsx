import { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import {
    Box,
    Button,
    Heading,
    Flex,
    Input,
    Text,
    VStack,
    HStack,
    useToast,
} from '@chakra-ui/react';
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

    const toast = useToast();

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const result = await getUsersApi(email, password);
                setUsers(result);
                setFilteredUsers(result);
            } catch (err) {
                setError('Failed to load users', err);
                toast({
                    title: 'Error.',
                    description: 'Failed to load users.',
                    status: 'error',
                    duration: 2000,
                    isClosable: true,
                });
            }
        };
        fetchUsers();
    }, [email, password, toast]);

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
            toast({
                title: 'Success.',
                description: `User ${newUser.name} added successfully.`,
                status: 'success',
                duration: 2000,
                isClosable: true,
            });
        } catch (err) {
            setError('Failed to add user', err);
            toast({
                title: 'Error.',
                description: 'Failed to add user.',
                status: 'error',
                duration: 2000,
                isClosable: true,
            });
        }
    };

    const handleDeleteUser = async (userId) => {
        try {
            await deleteUserApi(userId, email, password);
            setUsers(users.filter((user) => user.id !== userId));
            toast({
                title: 'Success.',
                description: 'User deleted successfully.',
                status: 'success',
                duration: 2000,
                isClosable: true,
            });
        } catch (err) {
            setError('Failed to delete user', err);
            toast({
                title: 'Error.',
                description: 'Failed to delete user.',
                status: 'error',
                duration: 2000,
                isClosable: true,
            });
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
            toast({
                title: 'Success.',
                description: `User ${newUser.name} updated successfully.`,
                status: 'success',
                duration: 2000,
                isClosable: true,
            });
        } catch (err) {
            setError('Failed to update user', err);
            toast({
                title: 'Error.',
                description: 'Failed to update user.',
                status: 'error',
                duration: 2000,
                isClosable: true,
            });
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
        return <Text color='red.500'>{error}</Text>;
    }

    return (
        <Box>
            {!showForm && (
                <>
                    <Heading as='h2' size='lg'>
                        Users Data:
                    </Heading>

                    <SearchBar
                        value={searchTerm}
                        onChange={setSearchTerm}
                        placeholder='Search users...'
                    />
                    <Text mb={3}>Or: </Text>
                    <Button mb={5} onClick={() => setShowForm(true)}>
                        Add New User
                    </Button>
                </>
            )}

            {showForm && (
                <VStack spacing={4} mt={4}>
                    <Heading as='h3' size='md'>
                        {editingUser ? 'Edit User' : 'Add New User'}
                    </Heading>
                    <Input
                        placeholder='Name'
                        value={newUser.name}
                        onChange={(e) =>
                            setNewUser({ ...newUser, name: e.target.value })
                        }
                    />
                    <Input
                        placeholder='Last Name'
                        value={newUser.lastName}
                        onChange={(e) =>
                            setNewUser({ ...newUser, lastName: e.target.value })
                        }
                    />
                    <Input
                        placeholder='Email'
                        value={newUser.email}
                        onChange={(e) =>
                            setNewUser({ ...newUser, email: e.target.value })
                        }
                    />
                    <Input
                        placeholder='Password'
                        type='password'
                        value={newUser.password}
                        onChange={(e) =>
                            setNewUser({ ...newUser, password: e.target.value })
                        }
                    />
                    <Input
                        placeholder='Role ID'
                        value={newUser.role}
                        onChange={(e) =>
                            setNewUser({ ...newUser, role: +e.target.value })
                        }
                    />

                    <HStack spacing={4}>
                        <Button
                            colorScheme='pink'
                            onClick={
                                editingUser ? handleSaveEditUser : handleAddUser
                            }
                            mr={4}
                        >
                            {editingUser ? 'Save' : 'Submit'}
                        </Button>
                        <Button onClick={handleCancelEdit} colorScheme='teal'>
                            Cancel
                        </Button>
                    </HStack>
                </VStack>
            )}

            {!showForm && (
                <HStack wrap='wrap' justifyContent='center' spacing={4} mt={4}>
                    {filteredUsers.map((user) => (
                        <Box
                            key={user.id}
                            borderWidth='1px'
                            borderRadius='lg'
                            boxShadow='sm'
                            width='300px'
                            p={4}
                        >
                            <Heading as='h3' size='md' color='#38b3b0'>
                                {`${user.name} ${user.lastName}`}
                            </Heading>
                            <Text>Email: {user.email}</Text>
                            <Text>Role: {user.role.name}</Text>

                            <Flex justifyContent='space-around' mt={4}>
                                <Button
                                    onClick={() => handleDeleteUser(user.id)}
                                    colorScheme='pink'
                                >
                                    Delete
                                </Button>
                                <Button
                                    onClick={() => handleEditUser(user)}
                                    colorScheme='teal'
                                >
                                    Edit
                                </Button>
                            </Flex>
                        </Box>
                    ))}
                </HStack>
            )}
        </Box>
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

export default UserCard;
