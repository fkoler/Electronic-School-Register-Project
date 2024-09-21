import { useState, useEffect } from 'react';

import PropTypes from 'prop-types';

import useAuthStore from '../../utils/auth/useAuthStore';
import { getClassesApi } from '../../services/api/api';

import SearchBar from '../SearchBar/searchbar.component';

const ClassCard = () => {
    const [classes, setClasses] = useState([]);
    const [filteredClasses, setFilteredClasses] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [error, setError] = useState(null);

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

    if (error) {
        return <p>{error}</p>;
    }

    return (
        <div>
            <h2>Classes Data:</h2>

            <SearchBar value={searchTerm} onChange={setSearchTerm} />

            <div style={{ display: 'flex', flexWrap: 'wrap' }}>
                {filteredClasses.map((classItem) => (
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
