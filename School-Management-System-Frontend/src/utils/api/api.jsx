export const getUsers = async (username, password) => {
    try {
        const response = await fetch('http://localhost:8080/api/v1/users', {
            method: 'GET',
            headers: {
                Authorization: 'Basic ' + btoa(`${username}:${password}`),
                'Content-Type': 'application/json',
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
