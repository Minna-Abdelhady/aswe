package com.example.aswe.microservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;
    private String name;
    private int quantity;
  
    // @OneToMany
    // private List<CartProducts> cartProductsList;
    
    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    public ShoppingCart() {
    }

    public ShoppingCart(int cartId, String name, int quantity, User user, Product product) {
        this.cartId = cartId;
        this.name = name;
        this.quantity = quantity;
        this.user = user;
        this.product = product;
    }

    public int getCartId() {
        return this.cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public ShoppingCart cartId(int cartId) {
        setCartId(cartId);
        return this;
    }

    public ShoppingCart name(String name) {
        setName(name);
        return this;
    }

    public ShoppingCart quantity(int quantity) {
        setQuantity(quantity);
        return this;
    }

    public ShoppingCart user(User user) {
        setUser(user);
        return this;
    }

    public ShoppingCart product(Product product) {
        setProduct(product);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ShoppingCart)) {
            return false;
        }
        ShoppingCart shoppingCart = (ShoppingCart) o;
        return cartId == shoppingCart.cartId && Objects.equals(name, shoppingCart.name) && quantity == shoppingCart.quantity && Objects.equals(user, shoppingCart.user) && Objects.equals(product, shoppingCart.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, name, quantity, user, product);
    }

    @Override
    public String toString() {
        return "{" +
            " cartId='" + getCartId() + "'" +
            ", name='" + getName() + "'" +
            ", quantity='" + getQuantity() + "'" +
            ", user='" + getUser() + "'" +
            ", product='" + getProduct() + "'" +
            "}";
    }
}

    // public ShoppingCart() {
    // }

    // public ShoppingCart(int cartId, String name,List<CartProducts> cartProductsList) {
    //     this.cartId = cartId;
    //     this.name = name;
    //     this.cartProductsList=cartProductsList;
    // }   
    // public User getUser(){
    //     return user;
    // }
    // public void setUser(User user){
    //     this.user=user;
    // }
    // public Product getProduct(){
    //     return product;
    // }
    // public void setProduct(Product product){
    //     this.product=product;
    // }

    // public int getId() {
    //     return this.cartId;
    // }

    // public void setId(int id) {
    //     this.cartId = id;
    // }

    // public String getName() {
    //     return this.name;
    // }

    // public void setName(String name) {
    //     this.name = name;
    // }
    // public List<CartProducts> getCartProductsList() {
    //     return cartProductsList;
    // }

    // public void setCartProductsList(List <CartProducts> cartProductsList) {
    //     this.cartProductsList=cartProductsList;
    // }

    
    // // public void addProductToCart(CartProducts cartProduct){
    // //     if(cartProductsList==null){
    // //         cartProductsList=new ArrayList<>();
    // //     }
    // //     cartProductsList.add(cartProduct);   
    // // }
    

 
    //  public void removeProductFromCart(CartProducts cartproduct){
    //      if(cartProductsList!=null){
    //         cartProductsList.remove(cartproduct);
    //      }
    //  }
    //  }
 