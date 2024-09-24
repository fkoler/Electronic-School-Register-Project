import { useNavigate } from 'react-router-dom';
import { Flex, Text, Button } from '@chakra-ui/react';
import useAuthStore from '../../utils/auth/useAuthStore';

const Header = () => {
    const user = useAuthStore((state) => state.user);
    const navigate = useNavigate();

    const handleLogout = () => {
        useAuthStore.getState().logout();
        navigate('/');
    };

    const handleNavigate = () => {
        if (user.role.name === 'ROLE_ADMIN') {
            navigate('/admin');
        } else if (user.role.name === 'ROLE_TEACHER') {
            navigate('/teacher');
        }
    };

    return (
        <>
            {user ? (
                <Flex justify='space-between' align='center' p={4}>
                    <Text
                        as='h2'
                        onClick={handleNavigate}
                        cursor='pointer'
                        fontSize='xl'
                        color='#38b3b0'
                    >
                        {user.role.name === 'ROLE_ADMIN'
                            ? 'Admin Dashboard'
                            : 'Teacher Dashboard'}
                    </Text>

                    <Flex align='center'>
                        <Text mr={4}>{user.email}</Text>
                        <Button onClick={handleLogout} colorScheme='teal'>
                            Logout
                        </Button>
                    </Flex>
                </Flex>
            ) : null}
        </>
    );
};

export default Header;
