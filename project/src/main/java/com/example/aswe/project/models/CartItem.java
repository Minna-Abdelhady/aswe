package com.example.aswe.project.models;

import org.springframework.data.annotation.Id;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="cart_item")

public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_detailed_id")
    private long id;
    private int quantity;
    private double totalPrice;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="shopping_cart_id",referencedColumnName = "shopping_cart_id")
    private shoppingcart ShoppingCart;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="product_id",referencedColumnName = "product_id")
    private product Product;
} 
