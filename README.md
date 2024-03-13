# OOP-Assignment-1
# Library Management System Readme

## Key Classes and Purpose

### 1. `Book`
- **Purpose**: The `Book` class encapsulates details about a book within the library system, including its ID, title, author, genre, and availability status. It facilitates access to and modification of book information.

### 2. `Library`
- **Purpose**: The `Library` class oversees the management of both books and users in the library. It enables functionalities such as adding, searching, checking out, and returning books. Additionally, it handles the loading and saving of library data from and to files.

### 3. `User`
- **Purpose**: The `User` class represents individuals utilizing the library services. It stores essential user information like ID, name, contact details, and a record of borrowed books. This class provides methods to interact with and manipulate user data.

### 4. `LibraryManagementSystem`
- **Purpose**: The `LibraryManagementSystem` class extends the functionalities of the `Library` class by offering a graphical user interface (GUI) for seamless interaction with the library system. It initializes library data from files and enables users to perform various tasks such as managing books and users.

### 5. `Main`
- **Purpose**: The `Main` class serves as the entry point for launching the library management system. It creates an instance of the `LibraryManagementSystem` class and triggers its `start` method to initiate the GUI interface.

## Prerequisites to Run the Project

Before running the project, ensure you have the following prerequisites installed:

- Java Development Kit (JDK)
- Integrated Development Environment (IDE) for Java development (e.g., IntelliJ IDEA, Eclipse)
- Required Libraries: None

## Functionalities

1. **Adding Books and Users**:
   - Users can add new books and users via the GUI interface provided by the `LibraryManagementSystem` class.

2. **Checking Out and Returning Books**:
   - The system supports users in checking out and returning books by providing appropriate user ID and book ID inputs.

3. **Searching for Books**:
   - Users can search for books by title or author using the search functionalities provided by the `LibraryManagementSystem` class.

4. **Displaying Books and Users**:
   - The system offers options to display all books or users currently available in the library, facilitating easy access to inventory and user information.

5. **Loading and Saving Data**:
   - The `Library` class facilitates the loading of book and user data from external files into the system and the saving of any modifications back to the files, ensuring data persistence across sessions.

## Usage

1. Clone the repository to your local machine.
2. Open the project in your preferred Java IDE.
3. Compile and run the `Main` class to start the library management system.
4. Utilize the GUI interface to carry out various library operations as described above.

## Contributors

- Muhammad Taha Mukhtar

Contributions to this project are encouraged! Feel free to submit bug reports, feature requests, or pull requests.

---
This README provides an overview of the library management system project, detailing key classes and their purposes, prerequisites for running the project, and its functionalities. Customize it as necessary and consider adding additional sections or project-specific details.
