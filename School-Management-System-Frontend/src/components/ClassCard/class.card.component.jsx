import { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import {
    Box,
    Button,
    Input,
    Heading,
    Text,
    HStack,
    VStack,
    Flex,
    useToast,
} from '@chakra-ui/react';
import useAuthStore from '../../utils/auth/useAuthStore';
import {
    getClassesApi,
    postClassApi,
    deleteClassApi,
    putClassApi,
} from '../../services/api/api';

import SearchBar from '../SearchBar/searchbar.component';

const ClassCard = () => {
    const [classes, setClasses] = useState([]);
    const [filteredClasses, setFilteredClasses] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [error, setError] = useState(null);
    const [showForm, setShowForm] = useState(false);
    const [newClass, setNewClass] = useState({ name: '' });
    const [editingClass, setEditingClass] = useState(null);

    const email = useAuthStore((state) => state.user.email);
    const authPassword = useAuthStore((state) => state.user.password);
    const password = authPassword.replace('{noop}', '');

    const toast = useToast();

    useEffect(() => {
        const fetchClasses = async () => {
            try {
                const result = await getClassesApi(email, password);
                setClasses(result);
                setFilteredClasses(result);
            } catch (err) {
                setError('Failed to load classes', err);
                toast({
                    title: 'Error fetching classes',
                    description: 'There was an error fetching the class data.',
                    status: 'error',
                    duration: 2000,
                    isClosable: true,
                });
            }
        };

        fetchClasses();
    }, [email, password, toast]);

    const normalizeString = (str) => str.trim().replace(/\s+/g, ' ');

    useEffect(() => {
        const normalizedSearchTerm = normalizeString(searchTerm.toLowerCase());

        setFilteredClasses(
            classes.filter((classItem) =>
                normalizeString(classItem.name.toLowerCase()).includes(
                    normalizedSearchTerm
                )
            )
        );
    }, [searchTerm, classes]);

    const handleAddNewClass = async () => {
        const className = newClass.name.trim();

        if (!className) {
            toast({
                title: 'Validation Error',
                description: 'Class name cannot be empty.',
                status: 'error',
                duration: 2000,
                isClosable: true,
            });
            return;
        }

        if (className.length < 5 || className.length > 10) {
            toast({
                title: 'Validation Error',
                description:
                    'Class name must be between 5 and 10 characters long.',
                status: 'error',
                duration: 2000,
                isClosable: true,
            });
            return;
        }

        try {
            const result = await postClassApi(newClass, email, password);
            setClasses((prevClasses) => [...prevClasses, result]);
            setShowForm(false);
            setNewClass({ name: '' });
            toast({
                title: 'Class added',
                description: `Class "${newClass.name}" was successfully added.`,
                status: 'success',
                duration: 2000,
                isClosable: true,
            });
        } catch (err) {
            setError('Failed to add class', err);
            toast({
                title: 'Error adding class',
                description: 'There was an error adding the class.',
                status: 'error',
                duration: 2000,
                isClosable: true,
            });
        } finally {
            setSearchTerm('');
        }
    };

    const handleDeleteClass = async (classId) => {
        try {
            await deleteClassApi(email, password, classId);
            setClasses(classes.filter((classItem) => classItem.id !== classId));
            toast({
                title: 'Class deleted',
                description: 'The class was successfully deleted.',
                status: 'success',
                duration: 2000,
                isClosable: true,
            });
        } catch (err) {
            setError('Failed to delete class', err);
            toast({
                title: 'Error deleting class',
                description: 'There was an error deleting the class.',
                status: 'error',
                duration: 2000,
                isClosable: true,
            });
        } finally {
            setSearchTerm('');
        }
    };

    const handleEditClass = (classItem) => {
        setEditingClass(classItem);
        setShowForm(true);
        setSearchTerm('');
        setNewClass({
            name: classItem.name,
        });
    };

    const handleSaveEditClass = async () => {
        const className = newClass.name.trim();

        if (!className) {
            toast({
                title: 'Validation Error',
                description: 'Class name cannot be empty.',
                status: 'error',
                duration: 2000,
                isClosable: true,
            });
            return;
        }

        if (className.length < 5 || className.length > 10) {
            toast({
                title: 'Validation Error',
                description:
                    'Class name must be between 5 and 10 characters long.',
                status: 'error',
                duration: 2000,
                isClosable: true,
            });
            return;
        }

        try {
            await putClassApi(email, password, editingClass.id, newClass);
            setClasses((prevClasses) =>
                prevClasses.map((classItem) =>
                    classItem.id === editingClass.id
                        ? { ...classItem, ...newClass }
                        : classItem
                )
            );
            setEditingClass(null);
            setShowForm(false);
            setNewClass({ name: '' });
            toast({
                title: 'Class updated',
                description: `Class "${newClass.name}" was successfully updated.`,
                status: 'success',
                duration: 2000,
                isClosable: true,
            });
        } catch (err) {
            setError('Failed to update class', err);
            toast({
                title: 'Error updating class',
                description: 'There was an error updating the class.',
                status: 'error',
                duration: 2000,
                isClosable: true,
            });
        } finally {
            setSearchTerm('');
        }
    };

    const handleCancelEdit = () => {
        setEditingClass(null);
        setShowForm(false);
        setNewClass({ name: '' });
    };

    if (error) {
        return <Text color='red.500'>{error}</Text>;
    }

    return (
        <Box>
            {!showForm && (
                <>
                    <Heading as='h2' size='lg'>
                        Classes Data:
                    </Heading>

                    <SearchBar
                        value={searchTerm}
                        onChange={setSearchTerm}
                        placeholder='Search classes...'
                    />
                    <Text mb={3}>Or: </Text>
                    <Button mb={5} onClick={() => setShowForm(true)}>
                        Add New Class
                    </Button>
                </>
            )}

            {showForm && (
                <VStack spacing={4} mt={4}>
                    <Heading as='h3' size='md' mb={4}>
                        {editingClass ? 'Edit Class' : 'Add New Class'}
                    </Heading>
                    <Input
                        placeholder='Class Name'
                        value={newClass.name}
                        onChange={(e) =>
                            setNewClass({ ...newClass, name: e.target.value })
                        }
                        mb={4}
                    />
                    <HStack spacing={4}>
                        <Button
                            colorScheme='pink'
                            onClick={
                                editingClass
                                    ? handleSaveEditClass
                                    : handleAddNewClass
                            }
                            mr={4}
                        >
                            {editingClass ? 'Save' : 'Submit'}
                        </Button>
                        <Button colorScheme='teal' onClick={handleCancelEdit}>
                            Cancel
                        </Button>
                    </HStack>
                </VStack>
            )}

            {!showForm && (
                <HStack wrap='wrap' justifyContent='center' spacing={4} mt={4}>
                    {filteredClasses.map((classItem) => (
                        <Box
                            key={classItem.id}
                            borderWidth='1px'
                            borderRadius='lg'
                            boxShadow='sm'
                            width='300px'
                            p={4}
                        >
                            <Heading as='h3' size='md' color='#38b3b0'>
                                {classItem.name}
                            </Heading>
                            <Flex justifyContent='space-around' mt={4}>
                                <Button
                                    colorScheme='pink'
                                    onClick={() =>
                                        handleDeleteClass(classItem.id)
                                    }
                                >
                                    Delete
                                </Button>
                                <Button
                                    colorScheme='teal'
                                    onClick={() => handleEditClass(classItem)}
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

ClassCard.propTypes = {
    classes: PropTypes.arrayOf(
        PropTypes.shape({
            id: PropTypes.number.isRequired,
            name: PropTypes.string.isRequired,
        })
    ),
};

export default ClassCard;
