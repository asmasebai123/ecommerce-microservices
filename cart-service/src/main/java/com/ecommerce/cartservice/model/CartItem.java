package com.ecommerce.cartservice.model;

import jakarta.persistence.*;

/**
 * Entite JPA representant un article dans le panier.
 * Mappe sur la table "cart_items" dans la base H2 cartdb.
 *
 * Note : on stocke productName et productPrice localement
 * (denormalisation) pour eviter un appel HTTP synchrone
 * vers product-service a chaque lecture du panier.
 */
@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Double productPrice;

    @Column(nullable = false)
    private Integer quantity;

    // ---- Constructeurs ----

    public CartItem() {}

    public CartItem(Long productId, String productName, Double productPrice, Integer quantity) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    // ---- Getters & Setters ----

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItem{id=" + id + ", productId=" + productId
                + ", productName='" + productName + "', quantity=" + quantity + "}";
    }
}
