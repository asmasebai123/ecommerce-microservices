package com.ecommerce.productservice.repository;

import com.ecommerce.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository JPA pour les produits.
 * Spring Data JPA genere automatiquement les implementations CRUD :
 *   save(), findAll(), findById(), deleteById(), etc.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Aucune methode supplementaire necessaire pour ce TP
}
