import { useState, useEffect } from 'react';

import PropTypes from 'prop-types';

import useAuthStore from '../../features/auth/useAuthStore';
import { getSubjectsApi } from '../../services/api/api';

const SubjectCard = () => {
    const [subjects, setSubjects] = useState([]);
    const [error, setError] = useState(null);

    const email = useAuthStore((state) => state.user.email);
    const authPassword = useAuthStore((state) => state.user.password);
    const password = authPassword.replace('{noop}', '');

    useEffect(() => {
        const fetchSubjects = async () => {
            try {
                const result = await getSubjectsApi(email, password);
                setSubjects(result);
            } catch (err) {
                setError('Failed to load subjects', err);
            }
        };

        fetchSubjects();
    }, [email, password]);

    if (error) {
        return <p>{error}</p>;
    }

    return (
        <div>
            <h2>Subjects Data:</h2>
            <div style={{ display: 'flex', flexWrap: 'wrap' }}>
                {subjects.map((subject) => (
                    <div key={subject.id} style={styles.card}>
                        <h3>{subject.name}</h3>
                        <p>Weekly Fund: {subject.weeklyFund}</p>
                        <p>
                            Enrolled Students: {subject.enrolledStudents.length}
                        </p>
                        <p>
                            Teachers:{' '}
                            {subject.teachers
                                .map((teacher) => teacher.name)
                                .join(', ')}
                        </p>
                    </div>
                ))}
            </div>
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
