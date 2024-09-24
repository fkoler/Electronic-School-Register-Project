import PropTypes from 'prop-types';
import { FormControl, Input, FormLabel } from '@chakra-ui/react';

const SearchBar = ({ value, onChange, placeholder }) => {
    return (
        <FormControl>
            <FormLabel htmlFor='search' srOnly>
                Search
            </FormLabel>
            <Input
                id='search'
                type='text'
                padding={4}
                margin={4}
                width={400}
                borderRadius='md'
                borderColor='#f5f5f5'
                value={value}
                placeholder={placeholder}
                onChange={(e) => onChange(e.target.value)}
            />
        </FormControl>
    );
};

SearchBar.propTypes = {
    value: PropTypes.string.isRequired,
    onChange: PropTypes.func.isRequired,
    placeholder: PropTypes.string.isRequired,
};

export default SearchBar;
