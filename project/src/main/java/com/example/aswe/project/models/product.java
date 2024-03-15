package com.example.aswe.project.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class product {
       @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id;
        private String product_name;
        private String product_description;
        private String product_keyword;
        private String product_img;
        private double product_price;
        private String status;

    public String getProduct_name() {
        return this.product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_description() {
        return this.product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_keyword() {
        return this.product_keyword;
    }

    public void setProduct_keyword(String product_keyword) {
        this.product_keyword = product_keyword;
    }

    public String getProduct_img() {
        return this.product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public double getProduct_price() {
        return this.product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
        
}

