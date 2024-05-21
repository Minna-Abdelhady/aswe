package com.example.aswe.project.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class CartProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CartProducts_Id;
    private String name;
    private double price;
    private int quantity;
    private int shoppingCartId;
    @ManyToOne
    private ShoppingCart shoppingcart;

    @ManyToOne
    private User user;

    @ManyToOne
   private Product product;

    @OneToMany
    private List<Product>products= new ArrayList<>();

    public CartProducts(){

    }

    public CartProducts(int CartProducts_Id, String name, int price, int quantity,ShoppingCart shoppingcart, User user, Product product) {
        this.CartProducts_Id = CartProducts_Id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.shoppingcart =shoppingcart;
        this.user=user;
        this.product=product;
    }

    public int getId() {
        return this.CartProducts_Id;
    }

    public void setId(int CartProducts_Id) {
        this.CartProducts_Id = CartProducts_Id;
    }

    public String getName() {
        return this.name;
    }
    public int getshoppingCartId(){
 return this.shoppingCartId;
    }
    public void setShoppingCartId(int shoppingCartId){
  this.shoppingCartId=shoppingCartId;
    }
    public void setName(String name) {
        this.name = name;
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

    public ShoppingCart getShoppingCart() {
        return this.shoppingcart;
    }
    public void setShoppingCart(ShoppingCart shoppingcart) {
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
    public List<Product>getProducts(){
        return this.products;
    }
    public void setProducts(List<Product> products){
        this.products=products;
    }
    public void addProduct(Product product){
      products.add(product);
    }

    public Object stream() {
        throw new UnsupportedOperationException("Unimplemented method 'stream'");
    }
}
