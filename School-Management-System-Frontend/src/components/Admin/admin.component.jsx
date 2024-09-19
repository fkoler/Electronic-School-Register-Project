import { useState } from 'react';

import UserCard from './user.card.component';
import ClassCard from './class.card.component';
import SubjectCard from './subject.card.component';

const AdminDashboard = () => {
    const [type, setType] = useState('');

    const handleClick = (selectedType) => {
        setType(selectedType);
    };

    return (
        <div>
            <h1>Welcome Admin</h1>

            <div>
                <button onClick={() => handleClick('users')}>Users</button>
                <button onClick={() => handleClick('classes')}>Classes</button>
                <button onClick={() => handleClick('subjects')}>
                    Subjects
                </button>
            </div>

            {type === 'users' && <UserCard />}
            {type === 'classes' && <ClassCard />}
            {type === 'subjects' && <SubjectCard />}
        </div>
    );
};

export default AdminDashboard;
