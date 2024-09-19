import axios from 'axios';

const fetchWithTimeout = async (url, options, timeout = 500) => {
    return Promise.race([
        axios(url, options),
        new Promise((_, reject) =>
            setTimeout(() => reject(new Error('Request timed out.')), timeout)
        ),
    ]);
};

const apiCall = async (url, username, password) => {
    const credentials = btoa(`${username}:${password}`);

    try {
        const response = await fetchWithTimeout(url, {
            method: 'GET',
            headers: {
                Authorization: `Basic ${credentials}`,
            },
        });

        return response.data;
    } catch (error) {
        handleApiError(error);
    }
};

const handleApiError = (error) => {
    if (error.response) {
        if (error.response.status === 401) {
            throw new Error('Unauthorized. Please log in again.');
        } else if (
            error.response.status >= 400 &&
            error.response.status < 500
        ) {
            throw new Error('Client error occurred. Please try again.');
        } else if (error.response.status >= 500) {
            throw new Error('Server error. Please try again later.');
        }
    } else if (error.code === 'ECONNABORTED') {
        throw new Error('Network error. Please check your connection.');
    } else {
        console.error('Error during request:', error.message);
        throw error;
    }
};

export const loginApi = async (username, password) => {
    return apiCall('http://localhost:8080/api/v1/users', username, password);
};

export const getUsersApi = async (username, password) => {
    return apiCall('http://localhost:8080/api/v1/users', username, password);
};

export const getClassesApi = async (username, password) => {
    return apiCall('http://localhost:8080/api/v1/classes', username, password);
};

export const getSubjectsApi = async (username, password) => {
    return apiCall('http://localhost:8080/api/v1/subjects', username, password);
};
