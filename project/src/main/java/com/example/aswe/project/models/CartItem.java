package com.example.aswe.project.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

// import jakarta.annotation.sql.DataSourceDefinition;
// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.OneToOne;
// import jakarta.persistence.Table;


// @Entity
// @Table(name="cart_item")

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //@Column(name="order_detailed_id")
    @ManyToOne
    // @JoinColumn(name="shopping_cart_id")
    private shoppingcart ShoppingCart;
    @SuppressWarnings("unused")
    private Long productId;
    private int quantity;
    private double totalPrice;
   // @OneToOne(fetch = FetchType.EAGER)
   // @JoinColumn(name="product_id",referencedColumnName = "product_id")
    //private product Product;
     

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public shoppingcart getShoppingCart() {
        return this.ShoppingCart;
    }

    public void setShoppingCart(shoppingcart ShoppingCart) {
        this.ShoppingCart = ShoppingCart;
    }
}
