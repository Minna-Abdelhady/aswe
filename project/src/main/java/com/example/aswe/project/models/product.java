package com.example.aswe.project.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String brand;
    private String category;
    private double price;
    @Column(columnDefinition = "TEXT")
    private String description;
    // private Date createdAt;          // Will be added in the reports
    private String ImgFileName;

    public Product() {
    }

    public Product(int id, String name, String brand, String category, double price, String description, Date createdAt,
            String ImgFileName) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.description = description;
        this.ImgFileName = ImgFileName;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgFileName() {
        return this.ImgFileName;
    }

    public void setImgFileName(String ImgFileName) {
        this.ImgFileName = ImgFileName;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", name='" + getName() + "'" +
                ", brand='" + getBrand() + "'" +
                ", category='" + getCategory() + "'" +
                ", price='" + getPrice() + "'" +
                ", description='" + getDescription() + "'" +
                ", ImgFileName='" + getImgFileName() + "'" +
                "}";
    }

}