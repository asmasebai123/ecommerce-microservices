package com.ecommerce.cartservice.controller;

import com.ecommerce.cartservice.dto.AddToCartRequest;
import com.ecommerce.cartservice.model.CartItem;
import com.ecommerce.cartservice.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controleur REST du cart-service.
 *
 * Endpoints disponibles :
 *   POST   /api/cart              -> ajouter un produit au panier
 *   GET    /api/cart              -> voir le contenu du panier
 *   DELETE /api/cart/{productId}  -> supprimer un produit du panier
 *   DELETE /api/cart              -> vider tout le panier
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Ajouter un produit au panier.
     * Body: { "productId": 1, "productName": "Laptop", "productPrice": 999.99, "quantity": 2 }
     */
    @PostMapping
    public ResponseEntity<CartItem> addToCart(@RequestBody AddToCartRequest request) {
        CartItem cartItem = new CartItem(
                request.getProductId(),
                request.getProductName(),
                request.getProductPrice(),
                request.getQuantity()
        );
        CartItem saved = cartService.addToCart(cartItem);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    /**
     * Voir tous les articles dans le panier.
     */
    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItems() {
        return ResponseEntity.ok(cartService.getCartItems());
    }

    /**
     * Supprimer un produit specifique du panier par son productId.
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long productId) {
        cartService.removeFromCart(productId);
        return ResponseEntity.ok("Produit " + productId + " retire du panier.");
    }

    /**
     * Vider entierement le panier.
     */
    @DeleteMapping
    public ResponseEntity<String> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok("Panier vide avec succes.");
    }
}
