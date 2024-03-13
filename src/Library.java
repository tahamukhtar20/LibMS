import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Vector;
import java.awt.event.ActionListener;


public class Library {
    private final Vector<Book> books; // Create a Vector of Book objects

    private final Vector<User> users; // Create a Vector of User objects

    public Library() {
        // initialize the books and users vectors
        books = new Vector<>();
        users = new Vector<>();

        File file = new File("books.txt");
        if (file.exists()) {
            try {
                this.loadBooks(file);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        file = new File("users.txt");
        if (file.exists()) {
            try {
                this.loadUsers(file);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void checkoutBook(int userID, int bookID) {
        User user = getUserByID(userID);
        Book book = getBookByID(bookID);
        if (user != null && book != null) { // If the user and book are found
            if (book.getAvailabilityStatus()) {
                book.setAvailabilityStatus(false);
                Vector<Book> borrowedBooks = user.getBorrowedBooks(); // Get the user's borrowed books
                borrowedBooks.add(book);
            } else {
                throw new RuntimeException("Book not available"); // If the book is not available, throw an exception
            }
        } else {
            throw new RuntimeException("User or book not found"); // If the user or book is not found, throw an exception
        }
    }

    public void returnBook(int userID, int bookID) {
        User user = getUserByID(userID);
        Book book = getBookByID(bookID);
        if (user != null && book != null) {
            Vector<Book> borrowedBooks = user.getBorrowedBooks();
            if (borrowedBooks.contains(book)) {
                borrowedBooks.remove(book);
                book.setAvailabilityStatus(true);
            }
        } else {
            throw new RuntimeException("User or book not found"); // similar to above, the user or book is not found, throw an exception
        }
    }


    public Vector<Book> searchBookByTitle(String title) { // Create a method to search for a book by title
        Vector<Book> result = new Vector<>();
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                result.add(book);
            }
        }
        return result;
    }

    public Vector<Book> searchBookByAuthor(String author) { // Create a method to search for a book by author
        Vector<Book> result = new Vector<>();
        for (Book book : books) {
            if (book.getAuthor().equals(author)) {
                result.add(book);
            }
        }
        return result;
    }

    public User getUserByID(int userID) { // Create a method to get a user by ID
        for (User user : users) {
            if (user.getUserID() == userID) {
                return user;
            }
        }
        return null;
    }

    public Book getBookByID(int bookID) { // Create a method to get a book by ID
        for (Book book : books) {
            if (book.getBookID() == bookID) {
                return book;
            }
        }
        return null;
    }

    public Vector<Book> getBooks() {
        return books;
    } // Create a method to get the books

    public Vector<User> getUsers() {
        return users;
    } // Create a method to get the users

    public void loadBooks(File file) { // Create a method to load books from a file
        try {
            Scanner myReader = new Scanner(file);
            Vector<Book> books = new Vector<>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] bookData = data.split(",");
                int bookID = Integer.parseInt(bookData[0]);
                String title = bookData[1];
                String author = bookData[2];
                String genre = bookData[3];
                boolean availabilityStatus = Boolean.parseBoolean(bookData[4]);
                Book book = new Book(bookID, title, author, genre, availabilityStatus);
                this.addBook(book);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadUsers(File file) { // Create a method to load users from a file
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] userData = data.split(",");
                int userID = Integer.parseInt(userData[0]);
                String name = userData[1];
                String contactInformation = userData[2];
                Vector<Book> borrowedBooks = new Vector<>();
                User user = new User(userID, name, contactInformation, borrowedBooks);
                this.addUser(user);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveBooks(File file) { // Create a method to save books to a file
        try {
            java.io.PrintWriter output = new java.io.PrintWriter(file);
            for (Book book : books) {
                output.println(book.getBookID() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getGenre() + "," + book.getAvailabilityStatus());
            }
            output.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUsers(File file) { // Create a method to save users to a file
        try {
            java.io.PrintWriter output = new java.io.PrintWriter(file);
            for (User user : users) {
                output.println(user.getUserID() + "," + user.getName() + "," + user.getContactInformation());
            }
            output.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void addButton(Container container, String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setBackground(new Color(65, 105, 225));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        container.add(button);
    }

    public void start() {
        JFrame frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new GridLayout(0, 2));

        // add button
        addButton(frame, "Add Book", e -> {
            try {
                JPanel panel = new JPanel(new GridLayout(0, 2));
                JTextField titleField = new JTextField();
                JTextField authorField = new JTextField();
                JTextField genreField = new JTextField();

                panel.add(new JLabel("Title:"));
                panel.add(titleField);
                panel.add(new JLabel("Author:"));
                panel.add(authorField);
                panel.add(new JLabel("Genre:"));
                panel.add(genreField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Add Book",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String title = titleField.getText();
                    String author = authorField.getText();
                    String genre = genreField.getText();

                    if (title.isEmpty() || author.isEmpty() || genre.isEmpty()) {
                        throw new RuntimeException("Title, author, or genre not found");
                    }
                    Book book = new Book(this.getBooks().size() + 1, title, author, genre, true);
                    this.addBook(book);
                    this.saveBooks(new File("books.txt"));
                    this.saveUsers(new File("users.txt"));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        // add user button
        addButton(frame, "Add User", e -> {
            try {
                JPanel panel = new JPanel(new GridLayout(0, 2));
                JTextField nameField = new JTextField();
                JTextField contactInfoField = new JTextField();

                panel.add(new JLabel("Name:"));
                panel.add(nameField);
                panel.add(new JLabel("Contact Information:"));
                panel.add(contactInfoField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Add User",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String name = nameField.getText();
                    String contactInformation = contactInfoField.getText();

                    if (name.isEmpty() || contactInformation.isEmpty()) {
                        throw new RuntimeException("Name or contact information not found");
                    }
                    User user = new User(this.getUsers().size() + 1, name, contactInformation, new Vector<>());
                    this.addUser(user);
                    this.saveBooks(new File("books.txt"));
                    this.saveUsers(new File("users.txt"));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        // checkout book button
        addButton(frame, "Checkout Book", e -> {
            try {
                JPanel panel = new JPanel(new GridLayout(0, 2));
                JTextField userIDField = new JTextField();
                JTextField bookIDField = new JTextField();

                panel.add(new JLabel("User ID:"));
                panel.add(userIDField);
                panel.add(new JLabel("Book ID:"));
                panel.add(bookIDField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Checkout Book",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String userID = userIDField.getText();
                    String bookID = bookIDField.getText();

                    if (userID.isEmpty() || bookID.isEmpty()) {
                        throw new RuntimeException("User or book not found");
                    }
                    int iUserID = Integer.parseInt(userID);
                    int iBookID = Integer.parseInt(bookID);
                    this.checkoutBook(iUserID, iBookID);
                    this.saveBooks(new File("books.txt"));
                    this.saveUsers(new File("users.txt"));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        // return book button
        addButton(frame, "Return Book", e -> {
            try {
                JPanel panel = new JPanel(new GridLayout(0, 2));
                JTextField userIDField = new JTextField();
                JTextField bookIDField = new JTextField();

                panel.add(new JLabel("User ID:"));
                panel.add(userIDField);
                panel.add(new JLabel("Book ID:"));
                panel.add(bookIDField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Return Book",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String userID = userIDField.getText();
                    String bookID = bookIDField.getText();

                    if (userID.isEmpty() || bookID.isEmpty()) {
                        throw new RuntimeException("User or book not found");
                    }
                    int iUserID = Integer.parseInt(userID);
                    int iBookID = Integer.parseInt(bookID);
                    this.returnBook(iUserID, iBookID);
                    this.saveBooks(new File("books.txt"));
                    this.saveUsers(new File("users.txt"));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        // display books
        addButton(frame, "Display Books", e -> {
            try {
                String[] columnLabels = {"Book ID", "Title", "Author", "Genre", "Availability"};
                Object[][] rowData = new Object[this.getBooks().size()][5];

                int i = 0;
                for (Book book : this.getBooks()) {
                    rowData[i][0] = book.getBookID();
                    rowData[i][1] = book.getTitle();
                    rowData[i][2] = book.getAuthor();
                    rowData[i][3] = book.getGenre();
                    rowData[i][4] = book.getAvailabilityStatus();
                    i++;
                }

                if (rowData.length == 0) {
                    throw new RuntimeException("No books found");
                }

                JTable table = new JTable(rowData, columnLabels);
                JOptionPane.showMessageDialog(null, new JScrollPane(table));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });


        // display users
        addButton(frame, "Display Users", e -> {
            try {
                String[] columnLabels = {"User ID", "Name", "Contact Information", "Borrowed Books"};
                Object[][] rowData = new Object[this.getUsers().size()][4];

                int i = 0;
                for (User user : this.getUsers()) {
                    rowData[i][0] = user.getUserID();
                    rowData[i][1] = user.getName();
                    rowData[i][2] = user.getContactInformation();
                    rowData[i][3] = String.join(", ", user.getBorrowedBookNames());
                    i++;
                }

                if (rowData.length == 0) {
                    throw new RuntimeException("No users found");
                }

                JTable table = new JTable(rowData, columnLabels);
                JOptionPane.showMessageDialog(null, new JScrollPane(table));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        // search book by title
        addButton(frame, "Search Book By Title", e -> {
            try {
                String title = JOptionPane.showInputDialog("Enter title");
                String[] columnLabels = {"Book ID", "Title", "Author", "Genre", "Availability"};
                Vector<Book> searchResult = this.searchBookByTitle(title);
                if (searchResult.isEmpty() || searchResult == null) {
                    throw new RuntimeException("No books found");
                }
                Object[][] rowData = new Object[searchResult.size()][5];

                int i = 0;
                for (Book book : searchResult) {
                    rowData[i][0] = book.getBookID();
                    rowData[i][1] = book.getTitle();
                    rowData[i][2] = book.getAuthor();
                    rowData[i][3] = book.getGenre();
                    rowData[i][4] = book.getAvailabilityStatus();
                    i++;
                }

                JTable table = new JTable(rowData, columnLabels);
                JOptionPane.showMessageDialog(null, new JScrollPane(table));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        // search book by author
        addButton(frame, "Search Book By Author", e -> {
            try {
                String author = JOptionPane.showInputDialog("Enter author");
                String[] columnLabels = {"Book ID", "Title", "Author", "Genre", "Availability"};
                Vector<Book> searchResult = this.searchBookByAuthor(author);
                if (searchResult.isEmpty() || searchResult == null) {
                    throw new RuntimeException("No books found");
                }
                Object[][] rowData = new Object[searchResult.size()][5];

                int i = 0;
                for (Book book : searchResult) {
                    rowData[i][0] = book.getBookID();
                    rowData[i][1] = book.getTitle();
                    rowData[i][2] = book.getAuthor();
                    rowData[i][3] = book.getGenre();
                    rowData[i][4] = book.getAvailabilityStatus();
                    i++;
                }

                JTable table = new JTable(rowData, columnLabels);
                JOptionPane.showMessageDialog(null, new JScrollPane(table));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });


        frame.setVisible(true);
    }

}



