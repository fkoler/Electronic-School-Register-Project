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
    getSubjectsApi,
    postSubjectApi,
    deleteSubjectApi,
    putSubjectApi,
} from '../../services/api/api';

import SearchBar from '../SearchBar/searchbar.component';

const SubjectCard = () => {
    const [subjects, setSubjects] = useState([]);
    const [filteredSubjects, setFilteredSubjects] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [error, setError] = useState(null);
    const [showForm, setShowForm] = useState(false);
    const [newSubject, setNewSubject] = useState({ name: '', weeklyFund: '' });
    const [editingSubject, setEditingSubject] = useState(null);

    const toast = useToast();

    const email = useAuthStore((state) => state.user.email);
    const authPassword = useAuthStore((state) => state.user.password);
    const password = authPassword.replace('{noop}', '');

    useEffect(() => {
        const fetchSubjects = async () => {
            try {
                const result = await getSubjectsApi(email, password);
                setSubjects(result);
                setFilteredSubjects(result);
            } catch (err) {
                setError('Failed to load subjects', err);
                toast({
                    title: 'Error.',
                    description: 'Failed to load subjects.',
                    status: 'error',
                    duration: 2000,
                    isClosable: true,
                });
            }
        };

        fetchSubjects();
    }, [email, password, toast]);

    const normalizeString = (str) => str.trim().replace(/\s+/g, ' ');

    useEffect(() => {
        const normalizedSearchTerm = normalizeString(searchTerm.toLowerCase());

        setFilteredSubjects(
            subjects.filter((subject) =>
                normalizeString(subject.name.toLowerCase()).includes(
                    normalizedSearchTerm
                )
            )
        );
    }, [searchTerm, subjects]);

    const handleAddNewSubject = async () => {
        try {
            const result = await postSubjectApi(newSubject, email, password);
            setSubjects((prevSubjects) => [...prevSubjects, result]);
            setShowForm(false);
            setNewSubject({ name: '', weeklyFund: '' });
            toast({
                title: 'Subject added.',
                description: `New subject ${newSubject.name} has been added successfully.`,
                status: 'success',
                duration: 2000,
                isClosable: true,
            });
        } catch (err) {
            setError('Failed to add subject', err);
            toast({
                title: 'Error.',
                description: 'Failed to add subject.',
                status: 'error',
                duration: 2000,
                isClosable: true,
            });
        }
    };

    const handleDeleteSubject = async (subjectId) => {
        try {
            await deleteSubjectApi(email, password, subjectId);
            setSubjects(subjects.filter((subject) => subject.id !== subjectId));
            toast({
                title: 'Subject deleted.',
                description: 'Subject has been deleted successfully.',
                status: 'success',
                duration: 2000,
                isClosable: true,
            });
        } catch (err) {
            setError('Failed to delete subject', err);
            toast({
                title: 'Error.',
                description: 'Failed to delete subject.',
                status: 'error',
                duration: 2000,
                isClosable: true,
            });
        } finally {
            setSearchTerm('');
        }
    };

    const handleEditSubject = (subject) => {
        setEditingSubject(subject);
        setShowForm(true);
        setNewSubject({
            name: subject.name,
            weeklyFund: subject.weeklyFund,
        });
    };

    const handleSaveEditSubject = async () => {
        try {
            await putSubjectApi(email, password, editingSubject.id, newSubject);
            setSubjects((prevSubjects) =>
                prevSubjects.map((sub) =>
                    sub.id === editingSubject.id
                        ? { ...sub, ...newSubject }
                        : sub
                )
            );
            setEditingSubject(null);
            setShowForm(false);
            setNewSubject({ name: '', weeklyFund: '' });
            toast({
                title: 'Subject updated.',
                description: 'Subject details have been updated successfully.',
                status: 'success',
                duration: 5000,
                isClosable: true,
            });
        } catch (err) {
            setError('Failed to update subject', err);
            toast({
                title: 'Error.',
                description: 'Failed to update subject.',
                status: 'error',
                duration: 2000,
                isClosable: true,
            });
        }
    };

    const handleCancelEdit = () => {
        setEditingSubject(null);
        setShowForm(false);
        setNewSubject({ name: '', weeklyFund: '' });
    };

    if (error) {
        return <Text color='red.500'>{error}</Text>;
    }

    return (
        <Box>
            {!showForm && (
                <>
                    <Heading as='h2' size='lg'>
                        Subjects Data:
                    </Heading>

                    <SearchBar
                        value={searchTerm}
                        onChange={setSearchTerm}
                        placeholder='Search subjects...'
                    />
                    <Text mb={3}>Or:</Text>
                    <Button mb={5} onClick={() => setShowForm(true)}>
                        Add New Subject
                    </Button>
                </>
            )}

            {showForm && (
                <VStack spacing={4} mt={4}>
                    <Heading as='h3' size='md'>
                        {editingSubject ? 'Edit Subject' : 'Add New Subject'}
                    </Heading>

                    <Input
                        placeholder='Enter subject name'
                        value={newSubject.name}
                        onChange={(e) =>
                            setNewSubject({
                                ...newSubject,
                                name: e.target.value,
                            })
                        }
                    />

                    <Input
                        placeholder='Enter weekly fund'
                        value={newSubject.weeklyFund}
                        onChange={(e) =>
                            setNewSubject({
                                ...newSubject,
                                weeklyFund: +e.target.value,
                            })
                        }
                    />

                    <HStack spacing={4}>
                        <Button
                            colorScheme='pink'
                            onClick={
                                editingSubject
                                    ? handleSaveEditSubject
                                    : handleAddNewSubject
                            }
                        >
                            {editingSubject ? 'Save' : 'Submit'}
                        </Button>
                        <Button colorScheme='teal' onClick={handleCancelEdit}>
                            Cancel
                        </Button>
                    </HStack>
                </VStack>
            )}

            {!showForm && (
                <HStack wrap='wrap' justifyContent='center' spacing={4} mt={4}>
                    {filteredSubjects.map((subject) => (
                        <Box
                            key={subject.id}
                            borderWidth='1px'
                            borderRadius='lg'
                            boxShadow='sm'
                            width='300px'
                            p={4}
                        >
                            <Heading as='h3' size='md' color='#38b3b0'>
                                {subject.name}
                            </Heading>
                            <Text>Weekly Fund: {subject.weeklyFund}</Text>
                            <Text>
                                Enrolled Students:{' '}
                                {subject.enrolledStudents.length}
                            </Text>
                            <Text>
                                Teachers:{' '}
                                {subject.teachers
                                    .map((teacher) => teacher.name)
                                    .join(', ')}
                            </Text>
                            <Flex justifyContent='space-around' mt={4}>
                                <Button
                                    onClick={() =>
                                        handleDeleteSubject(subject.id)
                                    }
                                    colorScheme='pink'
                                >
                                    Delete
                                </Button>
                                <Button
                                    onClick={() => handleEditSubject(subject)}
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

SubjectCard.propTypes = {
    subjects: PropTypes.arrayOf(
        PropTypes.shape({
            id: PropTypes.number.isRequired,
            name: PropTypes.string.isRequired,
            weeklyFund: PropTypes.number.isRequired,
            enrolledStudents: PropTypes.arrayOf(
                PropTypes.shape({
                    id: PropTypes.number.isRequired,
                    name: PropTypes.string.isRequired,
                })
            ).isRequired,
            teachers: PropTypes.arrayOf(
                PropTypes.shape({
                    id: PropTypes.number.isRequired,
                    name: PropTypes.string.isRequired,
                })
            ).isRequired,
        })
    ),
};

export default SubjectCard;
