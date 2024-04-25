package com.example.aswe.project.models;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="product")
public class product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    private String productName;
    private String productDescription;
    private Double productPrice;
    private String status;
    // private byte[] productImg;
    // private String productImgUrl;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // public byte[] getProductImg() {
    //     return productImg;
    // }

    // public void setProductImg(byte[] productImg) {
    //     this.productImg = productImg;
    // }

    // public String getProductImgUrl() {
    //     return productImgUrl;
    // }

    // public void setProductImgUrl(String productImgUrl) {
    //     this.productImgUrl = productImgUrl;
    // }

    public static List<product> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

}
