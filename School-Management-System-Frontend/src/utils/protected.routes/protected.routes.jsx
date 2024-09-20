import { Outlet, Navigate } from 'react-router-dom';

import PropTypes from 'prop-types';

import useAuthStore from '../auth/useAuthStore';

const roleMap = {
    ROLE_ADMIN: 'admin',
    ROLE_TEACHER: 'teacher',
};

const ProtectedRoutes = ({ allowedRoles }) => {
    const user = useAuthStore((state) => state.user);
    const userRole = roleMap[user?.role?.name] || '';

    return allowedRoles.includes(user?.role?.name) ? (
        <Outlet />
    ) : (
        <Navigate to={`/${userRole}`} />
    );
};

ProtectedRoutes.propTypes = {
    allowedRoles: PropTypes.arrayOf(PropTypes.string).isRequired,
};

export default ProtectedRoutes;
