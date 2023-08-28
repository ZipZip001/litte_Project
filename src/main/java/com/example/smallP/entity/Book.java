package com.example.smallP.entity;

import jakarta.persistence.*;

@Entity
@Table(name= "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name= "bookname")
    private String bookname;
    @Column(name= "price")
    private String price;

    @Column(name= "description")
    private String description;

//    @Column(name= "categoryId")
//    private String categoryId;

    @Column(name= "author")
    private String author;

    public  Book(){

    }
    // define constructors

    public Book(String bookname, String price, String description, String author) {
        this.bookname = bookname;
        this.price = price;
        this.description = description;
//        this.categoryId = categoryId;
        this.author = author;
    }

    //Getter/Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public String getCategoryId() {
//        return categoryId;
//    }
//
//    public void setCategoryId(String categoryId) {
//        this.categoryId = categoryId;
//    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    //toString

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookname='" + bookname + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
//                ", categoryId='" + categoryId + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}

