import PropTypes from 'prop-types';

const SearchBar = ({ value, onChange }) => {
    return (
        <input
            type='text'
            placeholder='Search subjects...'
            value={value}
            onChange={(e) => onChange(e.target.value)}
            style={styles.searchInput}
        />
    );
};

SearchBar.propTypes = {
    value: PropTypes.string.isRequired,
    onChange: PropTypes.func.isRequired,
};

const styles = {
    searchInput: {
        padding: '8px',
        marginBottom: '16px',
        width: '100%',
        maxWidth: '300px',
        borderRadius: '4px',
        border: '1px solid #ccc',
    },
};

export default SearchBar;
