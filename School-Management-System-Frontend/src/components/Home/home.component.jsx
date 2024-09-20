import { useNavigate } from 'react-router-dom';

import useAuthStore from '../../utils/auth/useAuthStore';

const Home = () => {
    const navigate = useNavigate();
    const user = useAuthStore((state) => state.user);

    const goToLogin = () => {
        navigate('/login');
    };

    return (
        <div>
            <h1>Welcome to Electronic School-Diary</h1>
            {!user && <button onClick={goToLogin}>Go to Login</button>}
        </div>
    );
};

export default Home;
