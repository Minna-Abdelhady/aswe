package com.example.aswe.project.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class shoppingcart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    // @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    // private Set<CartItem> items = new HashSet<>();

    // public long getUserId() {
    //     return userId;
    // }

    // public void setUserId(long userId) {
    //     this.userId = userId;
    // }

    // public Set<CartItem> getItems() {
    //     return items;
    // }

    // public void setItems(Set<CartItem> items) {
    //     this.items = items;
    // }


    public shoppingcart() {
    }

    public shoppingcart(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public shoppingcart userId(long userId) {
        setUserId(userId);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof shoppingcart)) {
            return false;
        }
        shoppingcart shoppingcart = (shoppingcart) o;
        return userId == shoppingcart.userId;
    }

  

    @Override
    public String toString() {
        return "{" +
            " userId='" + getUserId() + "'" +
            "}";
    }
     
}