import { useState } from 'react';
import useAuthStore from '../../utils/auth/useAuthStore';
import { loginApi } from '../../services/api/api';
import {
    Box,
    Button,
    Flex,
    FormControl,
    FormLabel,
    Heading,
    Input,
    Text,
} from '@chakra-ui/react';

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
        <Flex direction='column' align='center' justify='center' height='100vh'>
            <Box
                p={6}
                borderRadius='md'
                boxShadow='xl'
                width={{ base: '90%', sm: '400px' }}
            >
                <Heading mb={4}>Login</Heading>
                {isUserLogedIn === null && (
                    <form onSubmit={handleSubmit}>
                        <FormControl id='email' mb={4} isRequired>
                            <FormLabel>Email:</FormLabel>
                            <Input
                                type='email'
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                            />
                        </FormControl>

                        <FormControl id='password' mb={4} isRequired>
                            <FormLabel>Password:</FormLabel>
                            <Input
                                type='password'
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                        </FormControl>

                        <Button type='submit' colorScheme='teal' width='full'>
                            Login
                        </Button>
                        {error && (
                            <Text color='red.500' mt={4}>
                                {error}
                            </Text>
                        )}
                    </form>
                )}
            </Box>
        </Flex>
    );
};

export default Login;
