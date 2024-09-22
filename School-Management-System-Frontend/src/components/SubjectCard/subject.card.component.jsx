import { useState, useEffect } from 'react';
import PropTypes from 'prop-types';

import useAuthStore from '../../utils/auth/useAuthStore';
import {
    getSubjectsApi,
    postSubjectApi,
    deleteSubjectApi,
} from '../../services/api/api';

import SearchBar from '../SearchBar/searchbar.component';

const SubjectCard = () => {
    const [subjects, setSubjects] = useState([]);
    const [filteredSubjects, setFilteredSubjects] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [error, setError] = useState(null);
    const [showForm, setShowForm] = useState(false);
    const [newSubject, setNewSubject] = useState({ name: '', weeklyFund: '' });

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
            }
        };

        fetchSubjects();
    }, [email, password]);

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
        } catch (err) {
            setError('Failed to add subject', err);
        }
    };

    const handleDeleteSubject = async (subjectId) => {
        try {
            await deleteSubjectApi(email, password, subjectId);
            setSubjects(subjects.filter((subject) => subject.id !== subjectId));
        } catch (err) {
            setError('Failed to delete subject', err);
        }
    };

    if (error) {
        return <p>{error}</p>;
    }

    return (
        <div>
            {!showForm && (
                <>
                    <h2>Subjects Data:</h2>
                    <SearchBar
                        value={searchTerm}
                        onChange={setSearchTerm}
                    />{' '}
                    <button onClick={() => setShowForm(!showForm)}>
                        Add New Subject
                    </button>
                </>
            )}

            {showForm && (
                <div style={{ marginTop: '20px' }}>
                    <h3>Add New Subject</h3>

                    <input
                        type='text'
                        placeholder='Subject Name'
                        value={newSubject.name}
                        onChange={(e) =>
                            setNewSubject({
                                ...newSubject,
                                name: e.target.value,
                            })
                        }
                    />

                    <input
                        type='text'
                        placeholder='Weekly Fund'
                        value={newSubject.weeklyFund}
                        onChange={(e) =>
                            setNewSubject({
                                ...newSubject,
                                weeklyFund: +e.target.value,
                            })
                        }
                    />

                    <button onClick={handleAddNewSubject}>Submit</button>
                </div>
            )}

            {!showForm && (
                <div style={{ display: 'flex', flexWrap: 'wrap' }}>
                    {filteredSubjects.map((subject) => (
                        <div key={subject.id} style={styles.card}>
                            <h3>{subject.name}</h3>
                            <p>Weekly Fund: {subject.weeklyFund}</p>
                            <p>
                                Enrolled Students:{' '}
                                {subject.enrolledStudents.length}
                            </p>
                            <p>
                                Teachers:{' '}
                                {subject.teachers
                                    .map((teacher) => teacher.name)
                                    .join(', ')}
                            </p>
                            <button
                                onClick={() => handleDeleteSubject(subject.id)}
                            >
                                Delete
                            </button>{' '}
                            <button>Edit</button>
                        </div>
                    ))}
                </div>
            )}
        </div>
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

export default SubjectCard;
