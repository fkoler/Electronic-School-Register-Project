import { useNavigate } from 'react-router-dom';

import useAuthStore from '../../utils/auth/useAuthStore';

const Header = () => {
    const user = useAuthStore((state) => state.user);
    const navigate = useNavigate();

    const handleLogout = () => {
        useAuthStore.getState().logout();
        navigate('/');
    };

    const handleNavigate = () => {
        if (user.role.name === 'ROLE_ADMIN') {
            navigate('/admin');
        } else if (user.role.name === 'ROLE_TEACHER') {
            navigate('/teacher');
        }
    };

    return (
        <>
            {user ? (
                <header>
                    <div>
                        {user.role.name === 'ROLE_ADMIN' && (
                            <h2
                                onClick={handleNavigate}
                                style={{ cursor: 'pointer' }}
                            >
                                Admin Dashboard
                            </h2>
                        )}

                        {user.role.name === 'ROLE_TEACHER' && (
                            <h2
                                onClick={handleNavigate}
                                style={{ cursor: 'pointer' }}
                            >
                                Teacher Dashboard
                            </h2>
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
