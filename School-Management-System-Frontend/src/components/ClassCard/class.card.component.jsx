import { useState, useEffect } from 'react';
import PropTypes from 'prop-types';

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

    useEffect(() => {
        const fetchClasses = async () => {
            try {
                const result = await getClassesApi(email, password);
                setClasses(result);
                setFilteredClasses(result);
            } catch (err) {
                setError('Failed to load classes', err);
            }
        };

        fetchClasses();
    }, [email, password]);

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
        try {
            const result = await postClassApi(newClass, email, password);
            setClasses((prevClasses) => [...prevClasses, result]);
            setShowForm(false);
            setNewClass({ name: '' });
        } catch (err) {
            setError('Failed to add class', err);
        } finally {
            setSearchTerm('');
        }
    };

    const handleDeleteClass = async (classId) => {
        try {
            await deleteClassApi(email, password, classId);
            setClasses(classes.filter((classItem) => classItem.id !== classId));
        } catch (err) {
            setError('Failed to delete class', err);
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
        } catch (err) {
            setError('Failed to update class', err);
        }
    };

    const handleCancelEdit = () => {
        setEditingClass(null);
        setShowForm(false);
        setNewClass({ name: '' });
    };

    if (error) {
        return <p>{error}</p>;
    }

    return (
        <div>
            {!showForm && (
                <>
                    <h2>Classes Data:</h2>
                    <SearchBar
                        value={searchTerm}
                        onChange={setSearchTerm}
                        placeholder='Search classes...'
                    />{' '}
                    or:{' '}
                    <button onClick={() => setShowForm(true)}>
                        Add New Class
                    </button>
                </>
            )}

            {showForm && (
                <div style={{ marginTop: '20px' }}>
                    <h3>{editingClass ? 'Edit Class' : 'Add New Class'}</h3>

                    <input
                        type='text'
                        placeholder='Class Name'
                        value={newClass.name}
                        onChange={(e) =>
                            setNewClass({
                                ...newClass,
                                name: e.target.value,
                            })
                        }
                    />

                    <button
                        onClick={
                            editingClass
                                ? handleSaveEditClass
                                : handleAddNewClass
                        }
                    >
                        {editingClass ? 'Save' : 'Submit'}
                    </button>
                    <button onClick={handleCancelEdit}>Cancel</button>
                </div>
            )}

            {!showForm && (
                <div style={{ display: 'flex', flexWrap: 'wrap' }}>
                    {filteredClasses.map((classItem) => (
                        <div key={classItem.id} style={styles.card}>
                            <h3>{classItem.name}</h3>
                            <button
                                onClick={() => handleDeleteClass(classItem.id)}
                            >
                                Delete
                            </button>{' '}
                            <button onClick={() => handleEditClass(classItem)}>
                                Edit
                            </button>
                        </div>
                    ))}
                </div>
            )}
        </div>
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

export default ClassCard;
