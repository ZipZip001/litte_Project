package com.example.smallP.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name= "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Lob
    @Column(name = "thumnail")
    private String thumnail;

    @Column(name = "slide", columnDefinition = "JSON")
    private String slide;

    @Column(name = "maintext")
    private String maintext;

    @Column(name = "category")
    private String category;

    @Column(name = "author")
    private String author;

    @Column(name = "price", precision = 10, scale = 3)
    private double price;

    @Column(name = "sold")
    private int sold;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createAt;

    @Column(name = "update_at")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date updateAt;


    // default constructor
    public  Book(){

    }
    //constructor
    public Book(String thumnail, String slide, String maintext, String category, String author, double price, int sold, int quantity, Date createAt, Date updateAt) {
        this.thumnail = thumnail;
        this.slide = slide;
        this.maintext = maintext;
        this.category = category;
        this.author = author;
        this.price = price;
        this.sold = sold;
        this.quantity = quantity;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }


    // getter & setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThumnail() {
        return thumnail;
    }

    public void setThumnail(String thumnail) {
        this.thumnail = thumnail;
    }

    public String getSlide() {
        return slide;
    }

    public void setSlide(String slide) {
        this.slide = slide;
    }

    public String getMaintext() {
        return maintext;
    }

    public void setMaintext(String maintext) {
        this.maintext = maintext;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}

