package com.example.project3;

public class Books {
    private String id;
    private String title;
    private String author;
    private String pages;

    public Books() {

    }

    public Books(String id, String title, String author, String pages) {
       this.id = id;
       this.title = title;
       this.author = author;
       this.pages = pages;
    }

    public String getBookID() {
        return id;
    }

    public void setBookID(String id) {
        this.id = id;
    }

    public String getBookTitle() {
        return title;
    }

    public void setBookTitle(String title) {
        this.title = title;
    }

    public String getBookAuthor() {
        return author;
    }

    public void setBookAuthor(String author) {
        this.author = author;
    }

    public String getBookPages() {
        return pages;
    }

    public void setBookPages(String pages) {
        this.pages = pages;
    }
}
