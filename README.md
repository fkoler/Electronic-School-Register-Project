# Electronic School Register

This project is built upon the backend version created by my dear colleague: [Hunor Tot Bagi](https://github.com/HunorTotBagi/school-management-system)

## Running the Backend

To run the code, you need to install Lombok. You can follow this easy tutorial for this purpose: [How to Install Lombok in Eclipse STS (Spring Tool Suite) IDE
](https://www.youtube.com/watch?v=VR7VaiXHJEY)

### Features

-   **CRUD Operations**

    -   **Teachers:** Create, Read, Update, Delete
    -   **Subjects:** Create, Read, Update, Delete
    -   **Students:** Create, Read, Update, Delete
    -   **Parents:** Create, Read, Update, Delete

-   **Class Management**

    -   A student attends a class (department).
    -   A teacher teaches a subject to a class.

-   **Grading System**

    -   Add grading types (e.g., oral exam, written exam, homework).

-   **Authentication**

    -   Secure login and registration for users with Basic Auth.

-   **Views**

    -   Implement views for managing and displaying data.

-   **Email Notifications**

    -   Send email notifications when a teacher gives a grade to a student.

-   **Logging**

    -   Implement a logger to track system events and errors.
    -   Provide an endpoint to download the log file.

-   **Swagger**
    -   Swagger for generating all implemented endpoints.

## Running the Frontend

To run the frontend application, ensure you have [Node.js](https://nodejs.org/) and [Yarn](https://www.npmjs.com/package/yarn) installed. Then, follow these steps:

1. Clone the repository:
    ```bash
    git clone https://github.com/fkoler/Electronic-School-Register-Project.git
    ```
2. Navigate to the frontend directory:
    ```bash
    cd School-Management-System-Frontend
    ```
3. Install the dependencies:
    ```bash
    yarn
    ```
4. Start the development server:
    ```bash
    yarn dev
    ```

### Features

-   **User-Friendly Interface:**
    -   A clean and intuitive design for easy navigation.
-   **CRUD Operations:**
    -   Allows users to create, read, update, and delete information for teachers, students, subjects, and parents.
-   **Real-Time Notifications:**
    -   Users receive updates in real-time when changes occur.
-   **Authentication:**
    -   Secure login and registration process for users.
-   **Integration with Backend:**
    -   Seamless communication with the backend API for data management.
-   **Email Notifications:**
    -   Alerts for users regarding grades and other important updates.
