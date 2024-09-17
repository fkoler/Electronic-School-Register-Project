const fetchWithTimeout = (url, options, timeout = 1000) => {
    return Promise.race([
        fetch(url, options),
        new Promise((_, reject) =>
            setTimeout(
                () => reject(new Error('Login failed. Please try again.')),
                timeout
            )
        ),
    ]);
};

export const loginApi = async (username, password) => {
    const credentials = btoa(`${username}:${password}`);

    try {
        const response = await fetchWithTimeout(
            'http://localhost:8080/api/v1/users',
            {
                method: 'GET',
                headers: {
                    Authorization: `Basic ${credentials}`,
                },
            },
            1000
        );

        if (response.status === 401) {
            throw new Error(
                'Login failed. Please check your username and password.'
            );
        } else if (response.status >= 400 && response.status < 500) {
            throw new Error('Client error occurred. Please try again.');
        } else if (response.status >= 500) {
            throw new Error('Server error. Please try again later.');
        }

        if (!response.ok) {
            throw new Error('Failed to log in');
        }

        const data = await response.json();
        return data;
    } catch (error) {
        if (error.name === 'TypeError') {
            console.error('Network error during login:', error);
            throw new Error('Network error. Please check your connection.');
        } else {
            console.error('Error during login:', error.message);
            throw error;
        }
    }
};

export const getUsersApi = async (username, password) => {
    const credentials = btoa(`${username}:${password}`);

    try {
        const response = await fetchWithTimeout(
            'http://localhost:8080/api/v1/users',
            {
                method: 'GET',
                headers: {
                    Authorization: `Basic ${credentials}`,
                },
            },
            1000
        );

        if (response.status === 401) {
            throw new Error('Unauthorized. Please log in again.');
        } else if (response.status >= 400 && response.status < 500) {
            throw new Error('Client error occurred while fetching users.');
        } else if (response.status >= 500) {
            throw new Error('Server error. Please try again later.');
        }

        if (!response.ok) {
            throw new Error('Failed to fetch users');
        }

        const data = await response.json();
        return data;
    } catch (error) {
        if (error.name === 'TypeError') {
            console.error('Network error during fetching users:', error);
            throw new Error('Network error. Please check your connection.');
        } else {
            console.error('Error during fetching users:', error.message);
            throw error;
        }
    }
};

export const getClassesApi = async (username, password) => {
    try {
        const response = await fetch('http://localhost:8080/api/v1/classes', {
            method: 'GET',
            headers: {
                Authorization: 'Basic ' + btoa(`${username}:${password}`),
            },
        });

        if (!response.ok) {
            throw new Error('Failed to fetch classes');
        }

        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error fetching classes:', error);
        throw error;
    }
};

export const getSubjectsApi = async (username, password) => {
    try {
        const response = await fetch('http://localhost:8080/api/v1/subjects', {
            method: 'GET',
            headers: {
                Authorization: 'Basic ' + btoa(`${username}:${password}`),
            },
        });

        if (!response.ok) {
            throw new Error('Failed to fetch subjects');
        }

        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error fetching subjects:', error);
        throw error;
    }
};
