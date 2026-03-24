package com.ecommerce.cartservice.repository;

import com.ecommerce.cartservice.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository JPA pour les articles du panier.
 * Spring Data JPA genere automatiquement les implementations.
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    /**
     * Trouve un article par l'ID du produit associe.
     */
    Optional<CartItem> findByProductId(Long productId);

    /**
     * Supprime un article par l'ID du produit associe.
     */
    void deleteByProductId(Long productId);
}
