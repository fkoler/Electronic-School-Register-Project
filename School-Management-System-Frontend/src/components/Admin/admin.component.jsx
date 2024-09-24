import { useState } from 'react';
import { Button, Stack, Heading, Box } from '@chakra-ui/react';
import UserCard from '../UserCard/user.card.component';
import ClassCard from '../ClassCard/class.card.component';
import SubjectCard from '../SubjectCard/subject.card.component';

const AdminDashboard = () => {
    const [type, setType] = useState('');

    const handleClick = (selectedType) => {
        setType(selectedType);
    };

    return (
        <Box p={4}>
            <Heading mb={4}>Welcome Admin</Heading>

            <Stack direction='row' spacing={4} mb={4} justifyContent='center'>
                <Button onClick={() => handleClick('users')} colorScheme='teal'>
                    Users
                </Button>
                <Button
                    onClick={() => handleClick('classes')}
                    colorScheme='teal'
                >
                    Classes
                </Button>
                <Button
                    onClick={() => handleClick('subjects')}
                    colorScheme='teal'
                >
                    Subjects
                </Button>
            </Stack>

            {type === 'users' && <UserCard />}
            {type === 'classes' && <ClassCard />}
            {type === 'subjects' && <SubjectCard />}
        </Box>
    );
};

export default AdminDashboard;
