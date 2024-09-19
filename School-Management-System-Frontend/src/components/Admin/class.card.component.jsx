import { useState, useEffect } from 'react';

import PropTypes from 'prop-types';

import useAuthStore from '../../features/auth/useAuthStore';
import { getClassesApi } from '../../services/api/api';

const ClassCard = () => {
    const [classes, setClasses] = useState([]);
    const [error, setError] = useState(null);

    const email = useAuthStore((state) => state.user.email);
    const authPassword = useAuthStore((state) => state.user.password);
    const password = authPassword.replace('{noop}', '');

    useEffect(() => {
        const fetchClasses = async () => {
            try {
                const result = await getClassesApi(email, password);
                setClasses(result);
            } catch (err) {
                setError('Failed to load classes', err);
            }
        };

        fetchClasses();
    }, [email, password]);

    if (error) {
        return <p>{error}</p>;
    }

    return (
        <div>
            <h2>Classes Data:</h2>
            <div style={{ display: 'flex', flexWrap: 'wrap' }}>
                {classes.map((classItem) => (
                    <div key={classItem.id} style={styles.card}>
                        <h3>{classItem.name}</h3>
                    </div>
                ))}
            </div>
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
        width: '200px',
        display: 'inline-block',
    },
};

export default ClassCard;
