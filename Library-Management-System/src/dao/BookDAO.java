package dao;

import model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    public void addBook(Book book) throws SQLException {  // method to add books 
        String query = "INSERT INTO books (title, author, is_available) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();   //risky code to check for exceptions
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setBoolean(3, book.isAvailable());
            stmt.executeUpdate();
        }
    }

    public List<Book> getAvailableBooks() throws SQLException {              // check available books
        String query = "SELECT * FROM books WHERE is_available = true";
        List<Book> books = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();  
             ResultSet rs = stmt.executeQuery(query)) {                  //risky code to check for exceptions
            while (rs.next()) {
                books.add(new Book(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getBoolean("is_available")
                ));
            }
        }
        return books;
    }

    public boolean borrowBook(String title) throws SQLException {                 // borrow books
        String query = "UPDATE books SET is_available = false WHERE title = ? AND is_available = true";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {         //risky code to check for exceptions
            stmt.setString(1, title);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean returnBook(String title) throws SQLException {               // return books
        String query = "UPDATE books SET is_available = true WHERE title = ?";  // here I could use book number for multiple books with the same title
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            return stmt.executeUpdate() > 0;
        }
    }
}
