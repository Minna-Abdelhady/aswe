// package com.example.aswe.project.models;

<<<<<<< Updated upstream
// import java.util.Set;

// import jakarta.persistence.CascadeType;
// import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.OneToMany;
// import jakarta.persistence.OneToOne;

// @Entity
// public class shoppingcart {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private long userID;
//     private int productID;
//     private int quantity;
//     private double subtotal;
//     @OneToOne(fetch=FetchType.EAGER)
//     @JoinColumn(name="User_id",referencedColumnName = "User_id")
//      private User user;
//      @OneToMany(cascade = CascadeType.ALL,mappedBy = "cart")
//      private Set <CartItem> cartItem;
//     public long getUserID() {
//         return this.userID;
//     }

//     public void setUserID(long userID) {
//         this.userID = userID;
//     }

//     public long getProductID() {
//         return this.productID;
//     }

//     public void setProductID(int productID) {
//         this.productID = productID;
//     }

//     public int getQuantity() {
//         return this.quantity;
//     }

//     public void setQuantity(int quantity) {
//         this.quantity = quantity;
//     }

//     public double getSubtotal() {
//         return this.subtotal;
//     }

//     public void setSubtotal(double subtotal) {
//         this.subtotal = subtotal;
//     }

// }
=======
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.server.rpc.core.java.util.HashSet_ServerCustomFieldSerializer;

@Entity
public class shoppingcart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> items = new HashSet_ServerCustomFieldSerializer<>();

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Set<CartItem> getItems() {
        return items;
    }

    public void setItems(Set<CartItem> items) {
        this.items = items;
    }
}
>>>>>>> Stashed changes
