package com.example.aswe.project.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.Objects;

@Entity
public class CartProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CartProducts_Id;
    private double price;
    private int quantity;
    //private int shoppingCartId;
    @ManyToOne
    private ShoppingCart shoppingcart;

    @ManyToOne
    private User user;

    @ManyToOne
   private Product product;

//     @OneToMany
//     private List<Product>products= new ArrayList<>();

    public CartProducts(){

    }

    public CartProducts(int CartProducts_Id, double price, int quantity, ShoppingCart shoppingcart, User user, Product product) {
        this.CartProducts_Id = CartProducts_Id;
        this.price = price;
        this.quantity = quantity;
        this.shoppingcart = shoppingcart;
        this.user = user;
        this.product = product;
    }

    public int getId() {
        return this.CartProducts_Id;
    }

    public void setId(int CartProducts_Id) {
        this.CartProducts_Id = CartProducts_Id;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ShoppingCart getShoppingcart() {
        return this.shoppingcart;
    }

    public void setShoppingcart(ShoppingCart shoppingcart) {
        this.shoppingcart = shoppingcart;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CartProducts CartProducts_Id(int CartProducts_Id) {
        setId(CartProducts_Id);
        return this;
    }

    public CartProducts price(double price) {
        setPrice(price);
        return this;
    }

    public CartProducts quantity(int quantity) {
        setQuantity(quantity);
        return this;
    }

    public CartProducts shoppingcart(ShoppingCart shoppingcart) {
        setShoppingcart(shoppingcart);
        return this;
    }

    public CartProducts user(User user) {
        setUser(user);
        return this;
    }

    public CartProducts product(Product product) {
        setProduct(product);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CartProducts)) {
            return false;
        }
        CartProducts cartProducts = (CartProducts) o;
        return CartProducts_Id == cartProducts.CartProducts_Id && price == cartProducts.price && quantity == cartProducts.quantity && Objects.equals(shoppingcart, cartProducts.shoppingcart) && Objects.equals(user, cartProducts.user) && Objects.equals(product, cartProducts.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(CartProducts_Id, price, quantity, shoppingcart, user, product);
    }

    @Override
    public String toString() {
        return "{" +
            " CartProducts_Id='" + getId() + "'" +
            ", price='" + getPrice() + "'" +
            ", quantity='" + getQuantity() + "'" +
            ", shoppingcart='" + getShoppingcart() + "'" +
            ", user='" + getUser() + "'" +
            ", product='" + getProduct() + "'" +
            "}";
    }
    

    }
