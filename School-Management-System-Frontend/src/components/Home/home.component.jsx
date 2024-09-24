import { Button, Heading, Flex } from '@chakra-ui/react';
import { useNavigate } from 'react-router-dom';
import useAuthStore from '../../utils/auth/useAuthStore';

const Home = () => {
    const navigate = useNavigate();
    const user = useAuthStore((state) => state.user);

    const goToLogin = () => {
        navigate('/login');
    };

    return (
        <Flex direction='column' align='center' justify='center' height='100vh'>
            <Heading mb={4}>
                Welcome to Electronic <br /> School-Diary
            </Heading>
            {!user && (
                <Button onClick={goToLogin} colorScheme='teal'>
                    Go to Login
                </Button>
            )}
        </Flex>
    );
};

export default Home;
