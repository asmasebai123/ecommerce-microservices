package com.ecommerce.cartservice.service;

import com.ecommerce.cartservice.model.CartItem;
import com.ecommerce.cartservice.repository.CartItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Couche metier du cart-service.
 * Gere les operations CRUD sur le panier.
 */
@Service
public class CartService {

    private final CartItemRepository cartItemRepository;

    public CartService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    /**
     * Ajoute un produit au panier.
     * Si le produit est deja present, incremente la quantite.
     */
    public CartItem addToCart(CartItem cartItem) {
        Optional<CartItem> existing = cartItemRepository.findByProductId(cartItem.getProductId());

        if (existing.isPresent()) {
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + cartItem.getQuantity());
            return cartItemRepository.save(item);
        }

        return cartItemRepository.save(cartItem);
    }

    /**
     * Retourne tous les articles du panier.
     */
    public List<CartItem> getCartItems() {
        return cartItemRepository.findAll();
    }

    /**
     * Supprime un article specifique du panier par productId.
     */
    @Transactional
    public void removeFromCart(Long productId) {
        cartItemRepository.deleteByProductId(productId);
    }

    /**
     * Vide entierement le panier.
     */
    public void clearCart() {
        cartItemRepository.deleteAll();
    }
}
