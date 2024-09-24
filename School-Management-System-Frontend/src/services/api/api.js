import axios from 'axios';

const apiURL = 'http://localhost:8080/api/v1';

const fetchWithTimeout = async (url, options, timeout = 500) => {
    return Promise.race([
        axios(url, options),
        new Promise((_, reject) =>
            setTimeout(() => reject(new Error('Request timed out.')), timeout)
        ),
    ]);
};

const apiCall = async (url, method, username, password, data = null) => {
    const credentials = btoa(`${username}:${password}`);

    try {
        const response = await fetchWithTimeout(
            url,
            {
                method: method,
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Basic ${credentials}`,
                },
                data: data,
            },
            500
        );

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
    return apiCall(`${apiURL}/users`, 'GET', username, password);
};

export const getUsersApi = async (username, password) => {
    return apiCall(`${apiURL}/users`, 'GET', username, password);
};

export const postUserApi = async (userData, username, password) => {
    return apiCall(`${apiURL}/users`, 'POST', username, password, userData);
};

export const putUserApi = async (userId, userData, username, password) => {
    return apiCall(
        `${apiURL}/users/${userId}`,
        'PUT',
        username,
        password,
        userData
    );
};

export const deleteUserApi = async (userId, username, password) => {
    return apiCall(`${apiURL}/users/${userId}`, 'DELETE', username, password);
};

export const getClassesApi = async (username, password) => {
    return apiCall(`${apiURL}/classes`, 'GET', username, password);
};

export const postClassApi = async (classData, username, password) => {
    return apiCall(`${apiURL}/classes`, 'POST', username, password, classData);
};

export const putClassApi = async (username, password, classId, classData) => {
    return apiCall(
        `${apiURL}/classes/${classId}`,
        'PUT',
        username,
        password,
        classData
    );
};

export const deleteClassApi = async (username, password, classId) => {
    return apiCall(
        `${apiURL}/classes/${classId}`,
        'DELETE',
        username,
        password
    );
};

export const getSubjectsApi = async (username, password) => {
    return apiCall(`${apiURL}/subjects`, 'GET', username, password);
};

export const postSubjectApi = async (subjectData, username, password) => {
    return apiCall(
        `${apiURL}/subjects`,
        'POST',
        username,
        password,
        subjectData
    );
};

export const putSubjectApi = async (
    username,
    password,
    subjectId,
    subjectData
) => {
    return apiCall(
        `${apiURL}/subjects/${subjectId}`,
        'PUT',
        username,
        password,
        subjectData
    );
};

export const deleteSubjectApi = async (username, password, subjectId) => {
    return apiCall(
        `${apiURL}/subjects/${subjectId}`,
        'DELETE',
        username,
        password
    );
};
