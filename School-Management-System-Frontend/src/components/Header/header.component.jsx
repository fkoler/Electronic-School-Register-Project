import { useNavigate } from 'react-router-dom';

import useAuthStore from '../../features/auth/useAuthStore';

const Header = () => {
    const user = useAuthStore((state) => state.user);
    const navigate = useNavigate();

    const handleLogout = () => {
        useAuthStore.getState().logout();
        navigate('/');
    };

    return (
        <>
            {user ? (
                <header>
                    <div>
                        {user.role.name === 'ROLE_ADMIN' && (
                            <h2>Admin Dashboard</h2>
                        )}
                        {user.role.name === 'ROLE_TEACHER' && (
                            <h2>Teacher Dashboard</h2>
                        )}
                    </div>
                    <div>
                        <button onClick={handleLogout}>Logout</button>
                    </div>
                </header>
            ) : (
                <></>
            )}
        </>
    );
};

export default Header;
