/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author kinth
 */

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class librarianinterface extends JFrame {

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/smart_library";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public librarianinterface() {

        setTitle("Smart Library - Librarian Interface");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel(
                "SMART LIBRARY MANAGEMENT SYSTEM",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(
                BorderFactory.createEmptyBorder(20, 10, 20, 10)
        );

        mainPanel.add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(
                new GridLayout(4, 2, 15, 15)
        );

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(30, 50, 30, 50)
        );

        JButton addStudent = new JButton("Add Student");

        addStudent.addActionListener(e -> {

            JTextField studentIdField = new JTextField();
            JTextField nameField = new JTextField();
            JTextField emailField = new JTextField();
            JTextField courseField = new JTextField();
            JTextField semesterField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

            panel.add(new JLabel("Student ID:"));
            panel.add(studentIdField);

            panel.add(new JLabel("Name:"));
            panel.add(nameField);

            panel.add(new JLabel("Email:"));
            panel.add(emailField);

            panel.add(new JLabel("Course:"));
            panel.add(courseField);

            panel.add(new JLabel("Semester:"));
            panel.add(semesterField);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    panel,
                    "Add Student",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {

                String studentId = studentIdField.getText().trim();
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String course = courseField.getText().trim();
                String semesterText = semesterField.getText().trim();

                if (studentId.isEmpty() || name.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Student ID and Name are required."
                    );

                    return;
                }

                try {

                    int semester = Integer.parseInt(semesterText);

                    String sql =
                            "INSERT INTO students " +
                            "(student_id, name, email, course, semester) " +
                            "VALUES (?, ?, ?, ?, ?)";

                    Connection con = DriverManager.getConnection(
                            DB_URL,
                            DB_USER,
                            DB_PASSWORD
                    );

                    PreparedStatement pst =
                            con.prepareStatement(sql);

                    pst.setString(1, studentId);
                    pst.setString(2, name);
                    pst.setString(3, email);
                    pst.setString(4, course);
                    pst.setInt(5, semester);

                    pst.executeUpdate();

                    pst.close();
                    con.close();

                    JOptionPane.showMessageDialog(
                            this,
                            "Student added successfully!"
                    );

                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Semester must be a number."
                    );

                } catch (SQLException ex) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Database Error: " + ex.getMessage()
                    );
                }
            }
        });

        JButton viewStudents = new JButton("View Students");

        viewStudents.addActionListener(e -> {

            StringBuilder data = new StringBuilder();

            String sql =
                    "SELECT student_id, name, email, course, semester " +
                    "FROM students";

            try {

                Connection con = DriverManager.getConnection(
                        DB_URL,
                        DB_USER,
                        DB_PASSWORD
                );

                PreparedStatement pst =
                        con.prepareStatement(sql);

                ResultSet rs = pst.executeQuery();

                while (rs.next()) {

                    data.append("Student ID: ")
                            .append(rs.getString("student_id"))
                            .append("\n");

                    data.append("Name: ")
                            .append(rs.getString("name"))
                            .append("\n");

                    data.append("Email: ")
                            .append(rs.getString("email"))
                            .append("\n");

                    data.append("Course: ")
                            .append(rs.getString("course"))
                            .append("\n");

                    data.append("Semester: ")
                            .append(rs.getInt("semester"))
                            .append("\n");

                    data.append("--------------------------------\n");
                }

                rs.close();
                pst.close();
                con.close();

                if (data.length() == 0) {
                    data.append("No students found.");
                }

                JTextArea textArea =
                        new JTextArea(data.toString());

                textArea.setEditable(false);
                textArea.setFont(
                        new Font("Arial", Font.PLAIN, 16)
                );

                JScrollPane scrollPane =
                        new JScrollPane(textArea);

                scrollPane.setPreferredSize(
                        new Dimension(600, 400)
                );

                JOptionPane.showMessageDialog(
                        this,
                        scrollPane,
                        "Student Records",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Database Error: " + ex.getMessage()
                );
            }
        });

        JButton addBook = new JButton("Add Book");

        addBook.addActionListener(e -> {

            JTextField bookNameField = new JTextField();
            JTextField authorField = new JTextField();
            JTextField categoryField = new JTextField();

            JPanel panel =
                    new JPanel(new GridLayout(3, 2, 10, 10));

            panel.add(new JLabel("Book Name:"));
            panel.add(bookNameField);

            panel.add(new JLabel("Author:"));
            panel.add(authorField);

            panel.add(new JLabel("Category:"));
            panel.add(categoryField);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    panel,
                    "Add Book",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {

                String bookName =
                        bookNameField.getText().trim();

                String author =
                        authorField.getText().trim();

                String category =
                        categoryField.getText().trim();

                if (bookName.isEmpty() ||
                        author.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Book name and Author are required."
                    );

                    return;
                }

                try {

                    String sql =
                            "INSERT INTO books " +
                            "(book_name, author, category, available) " +
                            "VALUES (?, ?, ?, ?)";

                    Connection con =
                            DriverManager.getConnection(
                                    DB_URL,
                                    DB_USER,
                                    DB_PASSWORD
                            );

                    PreparedStatement pst =
                            con.prepareStatement(sql);

                    pst.setString(1, bookName);
                    pst.setString(2, author);
                    pst.setString(3, category);
                    pst.setBoolean(4, true);

                    pst.executeUpdate();

                    pst.close();
                    con.close();

                    JOptionPane.showMessageDialog(
                            this,
                            "Book added successfully!"
                    );

                } catch (SQLException ex) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Database Error: " + ex.getMessage()
                    );
                }
            }
        });

        JButton viewBooks = new JButton("View Books");

        viewBooks.addActionListener(e -> {

            StringBuilder data = new StringBuilder();

            String sql =
                    "SELECT id, book_name, author, category, available " +
                    "FROM books";

            try {

                Connection con =
                        DriverManager.getConnection(
                                DB_URL,
                                DB_USER,
                                DB_PASSWORD
                        );

                PreparedStatement pst =
                        con.prepareStatement(sql);

                ResultSet rs = pst.executeQuery();

                while (rs.next()) {

                    data.append("Book ID: ")
                            .append(rs.getInt("id"))
                            .append("\n");

                    data.append("Book Name: ")
                            .append(rs.getString("book_name"))
                            .append("\n");

                    data.append("Author: ")
                            .append(rs.getString("author"))
                            .append("\n");

                    data.append("Category: ")
                            .append(rs.getString("category"))
                            .append("\n");

                    data.append("Available: ")
                            .append(
                                    rs.getBoolean("available")
                                            ? "Yes"
                                            : "No"
                            )
                            .append("\n");

                    data.append("--------------------------------\n");
                }

                rs.close();
                pst.close();
                con.close();

                if (data.length() == 0) {
                    data.append("No books found.");
                }

                JTextArea textArea =
                        new JTextArea(data.toString());

                textArea.setEditable(false);
                textArea.setFont(
                        new Font("Arial", Font.PLAIN, 16)
                );

                JScrollPane scrollPane =
                        new JScrollPane(textArea);

                scrollPane.setPreferredSize(
                        new Dimension(600, 400)
                );

                JOptionPane.showMessageDialog(
                        this,
                        scrollPane,
                        "Book Records",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Database Error: " + ex.getMessage()
                );
            }
        });

        JButton issueBook = new JButton("Issue Book");

       

issueBook.addActionListener(e -> {

    JTextField studentIdField = new JTextField();
    JTextField bookIdField = new JTextField();
    JTextField dueDateField = new JTextField();

    JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

    panel.add(new JLabel("Student ID:"));
    panel.add(studentIdField);

    panel.add(new JLabel("Book ID:"));
    panel.add(bookIdField);

    panel.add(new JLabel("Due Date (YYYY-MM-DD):"));
    panel.add(dueDateField);

    int result = JOptionPane.showConfirmDialog(
            this,
            panel,
            "Issue Book",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
    );

    if (result != JOptionPane.OK_OPTION) {
        return;
    }

    String studentIdText = studentIdField.getText().trim();
    String bookIdText = bookIdField.getText().trim();
    String dueDateText = dueDateField.getText().trim();

    if (studentIdText.isEmpty()
            || bookIdText.isEmpty()
            || dueDateText.isEmpty()) {

        JOptionPane.showMessageDialog(
                this,
                "Please fill in all fields."
        );

        return;
    }

    int studentId;
    int bookId;

    try {

        studentId = Integer.parseInt(studentIdText);
        bookId = Integer.parseInt(bookIdText);

    } catch (NumberFormatException ex) {

        JOptionPane.showMessageDialog(
                this,
                "Student ID and Book ID must be numbers."
        );

        return;
    }

    java.sql.Date dueDate;

    try {

        dueDate = java.sql.Date.valueOf(dueDateText);

    } catch (IllegalArgumentException ex) {

        JOptionPane.showMessageDialog(
                this,
                "Please enter the date in YYYY-MM-DD format."
        );

        return;
    }

    java.sql.Connection con = null;

    try {

        con = java.sql.DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/smart_library",
                "root",
                "root"
        );

        String checkStudent =
                "SELECT id FROM students WHERE student_id = ?";

        java.sql.PreparedStatement studentStmt =
                con.prepareStatement(checkStudent);

        studentStmt.setInt(1, studentId);

        java.sql.ResultSet studentRs =
                studentStmt.executeQuery();

        if (!studentRs.next()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student not found."
            );

            studentRs.close();
            studentStmt.close();

            return;
        }

        int databaseStudentId =
                studentRs.getInt("id");

        studentRs.close();
        studentStmt.close();

        String checkBook =
                "SELECT id, available FROM books WHERE id = ?";

        java.sql.PreparedStatement bookStmt =
                con.prepareStatement(checkBook);

        bookStmt.setInt(1, bookId);

        java.sql.ResultSet bookRs =
                bookStmt.executeQuery();

        if (!bookRs.next()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Book not found."
            );

            bookRs.close();
            bookStmt.close();

            return;
        }

        boolean available =
                bookRs.getBoolean("available");

        bookRs.close();
        bookStmt.close();

        if (!available) {

            JOptionPane.showMessageDialog(
                    this,
                    "This book is already issued."
            );

            return;
        }

        String insertLoan =
                "INSERT INTO loans "
                + "(student_id, book_id, issue_date, due_date, status) "
                + "VALUES (?, ?, CURDATE(), ?, 'Issued')";

        java.sql.PreparedStatement loanStmt =
                con.prepareStatement(insertLoan);

        loanStmt.setInt(1, databaseStudentId);
        loanStmt.setInt(2, bookId);
        loanStmt.setDate(3, dueDate);

        loanStmt.executeUpdate();

        loanStmt.close();

        String updateBook =
                "UPDATE books "
                + "SET available = FALSE "
                + "WHERE id = ?";

        java.sql.PreparedStatement updateStmt =
                con.prepareStatement(updateBook);

        updateStmt.setInt(1, bookId);

        updateStmt.executeUpdate();

        updateStmt.close();

        con.close();

        JOptionPane.showMessageDialog(
                this,
                "Book issued successfully!"
        );

    } catch (java.sql.SQLException ex) {

        JOptionPane.showMessageDialog(
                this,
                "Database Error: " + ex.getMessage()
        );

        try {

            if (con != null) {
                con.close();
            }

        } catch (java.sql.SQLException closeEx) {

            closeEx.printStackTrace();
        }
    }
});
        JButton returnBook = new JButton("Return Book");

       


returnBook.addActionListener(e -> {

    JTextField studentIdField = new JTextField();
    JTextField bookIdField = new JTextField();

    JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

    panel.add(new JLabel("Student ID:"));
    panel.add(studentIdField);

    panel.add(new JLabel("Book ID:"));
    panel.add(bookIdField);

    int result = JOptionPane.showConfirmDialog(
            this,
            panel,
            "Return Book",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
    );

    if (result != JOptionPane.OK_OPTION) {
        return;
    }

    String studentIdText =
            studentIdField.getText().trim();

    String bookIdText =
            bookIdField.getText().trim();

    if (studentIdText.isEmpty() ||
            bookIdText.isEmpty()) {

        JOptionPane.showMessageDialog(
                this,
                "Please enter Student ID and Book ID."
        );

        return;
    }

    int studentId;
    int bookId;

    try {

        studentId = Integer.parseInt(studentIdText);
        bookId = Integer.parseInt(bookIdText);

    } catch (NumberFormatException ex) {

        JOptionPane.showMessageDialog(
                this,
                "Student ID and Book ID must be numbers."
        );

        return;
    }

    Connection con = null;

    try {

        con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/smart_library",
                "root",
                "root"
        );

        String findStudent =
                "SELECT id FROM students WHERE student_id = ?";

        PreparedStatement studentStmt =
                con.prepareStatement(findStudent);

        studentStmt.setInt(1, studentId);

        ResultSet studentRs =
                studentStmt.executeQuery();

        if (!studentRs.next()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student not found."
            );

            studentRs.close();
            studentStmt.close();
            con.close();

            return;
        }

        int databaseStudentId =
                studentRs.getInt("id");

        studentRs.close();
        studentStmt.close();

        String findLoan =
                "SELECT id, status FROM loans "
                + "WHERE student_id = ? "
                + "AND book_id = ? "
                + "AND status = 'Issued' "
                + "ORDER BY id DESC LIMIT 1";

        PreparedStatement loanStmt =
                con.prepareStatement(findLoan);

        loanStmt.setInt(1, databaseStudentId);
        loanStmt.setInt(2, bookId);

        ResultSet loanRs =
                loanStmt.executeQuery();

        if (!loanRs.next()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No active loan found for this "
                    + "student and book."
            );

            loanRs.close();
            loanStmt.close();
            con.close();

            return;
        }

        int loanId =
                loanRs.getInt("id");

        loanRs.close();
        loanStmt.close();

        String updateLoan =
                "UPDATE loans "
                + "SET status = 'Returned', "
                + "return_date = CURDATE() "
                + "WHERE id = ?";

        PreparedStatement updateLoanStmt =
                con.prepareStatement(updateLoan);

        updateLoanStmt.setInt(1, loanId);

        updateLoanStmt.executeUpdate();

        updateLoanStmt.close();

        String updateBook =
                "UPDATE books "
                + "SET available = TRUE "
                + "WHERE id = ?";

        PreparedStatement updateBookStmt =
                con.prepareStatement(updateBook);

        updateBookStmt.setInt(1, bookId);

        updateBookStmt.executeUpdate();

        updateBookStmt.close();

        con.close();

        JOptionPane.showMessageDialog(
                this,
                "Book returned successfully!"
        );

    } catch (SQLException ex) {

        JOptionPane.showMessageDialog(
                this,
                "Database Error: " + ex.getMessage()
        );

        try {

            if (con != null) {
                con.close();
            }

        } catch (SQLException closeEx) {

            closeEx.printStackTrace();
        }
    }
});


JButton reservations = new JButton("Reservations");

reservations.addActionListener(e -> {

    JTextField studentIdField = new JTextField();
    JTextField bookIdField = new JTextField();

    JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

    panel.add(new JLabel("Student ID:"));
    panel.add(studentIdField);

    panel.add(new JLabel("Book ID:"));
    panel.add(bookIdField);

    int result = JOptionPane.showConfirmDialog(
            this,
            panel,
            "Reserve Book",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
    );

    if (result != JOptionPane.OK_OPTION) {
        return;
    }

    String studentIdText = studentIdField.getText().trim();
    String bookIdText = bookIdField.getText().trim();

    if (studentIdText.isEmpty() || bookIdText.isEmpty()) {

        JOptionPane.showMessageDialog(
                this,
                "Please enter Student ID and Book ID."
        );

        return;
    }

    try {

        int studentId = Integer.parseInt(studentIdText);
        int bookId = Integer.parseInt(bookIdText);

        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/smart_library",
                "root",
                "root"
        );

        String checkStudent =
                "SELECT id FROM students WHERE student_id = ?";

        PreparedStatement studentStmt =
                con.prepareStatement(checkStudent);

        studentStmt.setInt(1, studentId);

        ResultSet studentRs =
                studentStmt.executeQuery();

        if (!studentRs.next()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student not found."
            );

            studentRs.close();
            studentStmt.close();
            con.close();

            return;
        }

        int databaseStudentId =
                studentRs.getInt("id");

        studentRs.close();
        studentStmt.close();

        String checkBook =
                "SELECT id FROM books WHERE id = ?";

        PreparedStatement bookStmt =
                con.prepareStatement(checkBook);

        bookStmt.setInt(1, bookId);

        ResultSet bookRs =
                bookStmt.executeQuery();

        if (!bookRs.next()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Book not found."
            );

            bookRs.close();
            bookStmt.close();
            con.close();

            return;
        }

        bookRs.close();
        bookStmt.close();

        String insertReservation =
                "INSERT INTO reservations " +
                "(student_id, book_id, reservation_date, status) " +
                "VALUES (?, ?, CURDATE(), 'Pending')";

        PreparedStatement reservationStmt =
                con.prepareStatement(insertReservation);

        reservationStmt.setInt(1, databaseStudentId);
        reservationStmt.setInt(2, bookId);

        reservationStmt.executeUpdate();

        reservationStmt.close();
        con.close();

        JOptionPane.showMessageDialog(
                this,
                "Book reserved successfully!"
        );

    } catch (NumberFormatException ex) {

        JOptionPane.showMessageDialog(
                this,
                "Student ID and Book ID must be numbers."
        );

    } catch (SQLException ex) {

        JOptionPane.showMessageDialog(
                this,
                "Database Error: " + ex.getMessage()
        );
    }
});





        JButton fines = new JButton("Fines");

        

fines.addActionListener(e -> {

    JTextField studentIdField = new JTextField();
    JTextField bookIdField = new JTextField();
    JTextField amountField = new JTextField();

    JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

    panel.add(new JLabel("Student ID:"));
    panel.add(studentIdField);

    panel.add(new JLabel("Book ID:"));
    panel.add(bookIdField);

    panel.add(new JLabel("Fine Amount:"));
    panel.add(amountField);

    int result = JOptionPane.showConfirmDialog(
            this,
            panel,
            "Add Fine",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
    );

    if (result != JOptionPane.OK_OPTION) {
        return;
    }

    String studentIdText = studentIdField.getText().trim();
    String bookIdText = bookIdField.getText().trim();
    String amountText = amountField.getText().trim();

    if (studentIdText.isEmpty()
            || bookIdText.isEmpty()
            || amountText.isEmpty()) {

        JOptionPane.showMessageDialog(
                this,
                "Please fill in all fields."
        );

        return;
    }

    int studentId;
    int bookId;
    double amount;

    try {

        studentId = Integer.parseInt(studentIdText);
        bookId = Integer.parseInt(bookIdText);
        amount = Double.parseDouble(amountText);

    } catch (NumberFormatException ex) {

        JOptionPane.showMessageDialog(
                this,
                "Student ID, Book ID and Fine Amount must be valid numbers."
        );

        return;
    }

    if (amount <= 0) {

        JOptionPane.showMessageDialog(
                this,
                "Fine amount must be greater than zero."
        );

        return;
    }

    Connection con = null;

    try {

        con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/smart_library",
                "root",
                "root"
        );

        String findStudent =
                "SELECT id FROM students WHERE student_id = ?";

        PreparedStatement studentStmt =
                con.prepareStatement(findStudent);

        studentStmt.setInt(1, studentId);

        ResultSet studentRs =
                studentStmt.executeQuery();

        if (!studentRs.next()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student not found."
            );

            studentRs.close();
            studentStmt.close();
            con.close();

            return;
        }

        int databaseStudentId =
                studentRs.getInt("id");

        studentRs.close();
        studentStmt.close();

        String findLoan =
                "SELECT id FROM loans "
                + "WHERE student_id = ? "
                + "AND book_id = ? "
                + "AND status = 'Issued' "
                + "ORDER BY id DESC LIMIT 1";

        PreparedStatement loanStmt =
                con.prepareStatement(findLoan);

        loanStmt.setInt(1, databaseStudentId);
        loanStmt.setInt(2, bookId);

        ResultSet loanRs =
                loanStmt.executeQuery();

        if (!loanRs.next()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No active loan found for this student and book."
            );

            loanRs.close();
            loanStmt.close();
            con.close();

            return;
        }

        int loanId =
                loanRs.getInt("id");

        loanRs.close();
        loanStmt.close();

        String insertFine =
                "INSERT INTO fines "
                + "(loan_id, amount, payment_status) "
                + "VALUES (?, ?, 'Unpaid')";

        PreparedStatement fineStmt =
                con.prepareStatement(insertFine);

        fineStmt.setInt(1, loanId);
        fineStmt.setDouble(2, amount);

        fineStmt.executeUpdate();

        fineStmt.close();
        con.close();

        JOptionPane.showMessageDialog(
                this,
                "Fine added successfully!"
        );

    } catch (SQLException ex) {

        JOptionPane.showMessageDialog(
                this,
                "Database Error: " + ex.getMessage()
        );

        try {

            if (con != null) {
                con.close();
            }

        } catch (SQLException closeEx) {

            closeEx.printStackTrace();
        }
    }
});

        buttonPanel.add(addStudent);
        buttonPanel.add(viewStudents);
        buttonPanel.add(addBook);
        buttonPanel.add(viewBooks);
        buttonPanel.add(issueBook);
        buttonPanel.add(returnBook);
        buttonPanel.add(reservations);
        buttonPanel.add(fines);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);

        setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new librarianinterface();
        });
    }
}
