import { useNavigate } from 'react-router-dom';

const Home = () => {
    const navigate = useNavigate();

    const goToLogin = () => {
        navigate('/login');
    };

    return (
        <div>
            <h1>Welcome to Electronic School-Diary</h1>
            <button onClick={goToLogin}>Go to Login</button>
        </div>
    );
};

export default Home;
