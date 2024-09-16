export const loginApi = async (username, password) => {
    try {
        const response = await fetch('http://localhost:8080/api/v1/users', {
            method: 'GET',
            headers: {
                Authorization: 'Basic ' + btoa(`${username}:${password}`),
            },
        });

        if (!response.ok) {
            throw new Error('Failed to log in');
        }

        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error during login:', error);
        throw error;
    }
};

export const getUsersApi = async (username, password) => {
    try {
        const response = await fetch('http://localhost:8080/api/v1/users', {
            method: 'GET',
            headers: {
                Authorization: 'Basic ' + btoa(`${username}:${password}`),
            },
        });

        if (!response.ok) {
            throw new Error('Failed to fetch users');
        }

        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error during fetching users:', error);
        throw error;
    }
};
