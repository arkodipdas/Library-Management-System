package ui;

import dao.BookDAO;
import model.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LibraryApp {
    private BookDAO bookDAO = new BookDAO();

    public static void main(String[] args) {
        new LibraryApp().initUI();
    }

    public void initUI() {            //user interface for lms
        JFrame frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JButton addBookButton = new JButton("Add Book");// all the buttons for event listening activities 
        JButton borrowBookButton = new JButton("Borrow Book");
        JButton returnBookButton = new JButton("Return Book");
        JButton viewBooksButton = new JButton("View Available Books");

        panel.add(addBookButton);
        panel.add(borrowBookButton);
        panel.add(returnBookButton);
        panel.add(viewBooksButton);

        frame.add(panel);

        addBookButton.addActionListener(new ActionListener() {  //add a new book
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = JOptionPane.showInputDialog("Enter Title:");
                String author = JOptionPane.showInputDialog("Enter Author:");
                try {
                    bookDAO.addBook(new Book(title, author, true));
                    JOptionPane.showMessageDialog(null, "Book added successfully!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error adding book: " + ex.getMessage());
                }
            }
        });

        borrowBookButton.addActionListener(new ActionListener() { // borrow a book with a specific title
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = JOptionPane.showInputDialog("Enter Title of the book to borrow:");
                try {
                    if (bookDAO.borrowBook(title)) {
                        JOptionPane.showMessageDialog(null, "Book borrowed successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Book not available!");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error borrowing book: " + ex.getMessage());
                }
            }
        });

        returnBookButton.addActionListener(new ActionListener() {   //return a book with a specific title
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = JOptionPane.showInputDialog("Enter Title of the book to return:");
                try {
                    if (bookDAO.returnBook(title)) {
                        JOptionPane.showMessageDialog(null, "Book returned successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Book not found!");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error returning book: " + ex.getMessage());
                }
            }
        });

        viewBooksButton.addActionListener(new ActionListener() {   //view aal the books
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    StringBuilder booksList = new StringBuilder("Available Books:\n");
                    for (Book book : bookDAO.getAvailableBooks()) {
                        booksList.append(book.getTitle()).append(" by ").append("\t").append(book.getAuthor()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, booksList.toString());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error fetching books: " + ex.getMessage());
                }
            }
        });

        frame.setVisible(true);
    }
}
