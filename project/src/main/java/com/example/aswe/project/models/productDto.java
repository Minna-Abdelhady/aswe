package com.example.aswe.project.models;
import java.util.Objects;

import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class productDto {
    @NotEmpty(message = "Product name cannot be empty")
    private String name;
    @NotEmpty(message = "Brand name cannot be empty")
    private String brand;
    @NotEmpty(message = "Product name cannot be empty")
    private String category;
    @Min(0)
    private double price;
    @Size(min=10 , message="the description should be at least 10", max = 0)
    @Size(max=100 , message="the description should be at most 100")
    private String description;

    private MultipartFile imagFile;


    public productDto() {
    }

    public productDto(String name, String brand, String category, double price, String description, MultipartFile imagFile) {
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.description = description;
        this.imagFile = imagFile;
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

    public MultipartFile getImagFile() {
        return this.imagFile;
    }

    public void setImagFile(MultipartFile imagFile) {
        this.imagFile = imagFile;
    }

    public productDto name(String name) {
        setName(name);
        return this;
    }

    public productDto brand(String brand) {
        setBrand(brand);
        return this;
    }

    public productDto category(String category) {
        setCategory(category);
        return this;
    }

    public productDto price(double price) {
        setPrice(price);
        return this;
    }

    public productDto description(String description) {
        setDescription(description);
        return this;
    }

    public productDto imagFile(MultipartFile imagFile) {
        setImagFile(imagFile);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof productDto)) {
            return false;
        }
        productDto productDto = (productDto) o;
        return Objects.equals(name, productDto.name) && Objects.equals(brand, productDto.brand) && Objects.equals(category, productDto.category) && price == productDto.price && Objects.equals(description, productDto.description) && Objects.equals(imagFile, productDto.imagFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, brand, category, price, description, imagFile);
    }

    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", brand='" + getBrand() + "'" +
            ", category='" + getCategory() + "'" +
            ", price='" + getPrice() + "'" +
            ", description='" + getDescription() + "'" +
            ", imagFile='" + getImagFile() + "'" +
            "}";
    }
    

}