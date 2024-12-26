package model;

public class Book {
    private String title;    //instance variables
    private String author;
    private boolean isAvailable;

    public Book(String title, String author, boolean isAvailable)  //parameterized constructor
    {
      
        this.title = title;
        this.author = author;
        this.isAvailable = isAvailable;
    }

    public String getTitle() {    //getter methods
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {            //set the availability
        this.isAvailable = isAvailable;
    }
}
