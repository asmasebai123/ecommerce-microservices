package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controleur REST du product-service.
 *
 * Endpoints disponibles :
 *   POST   /api/products        -> ajouter un produit
 *   POST   /api/products/batch  -> ajouter plusieurs produits
 *   GET    /api/products        -> lister tous les produits
 *   GET    /api/products/{id}   -> obtenir un produit par ID
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Ajouter un seul produit.
     * Body: { "name": "Laptop", "price": 999.99, "description": "..." }
     */
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product saved = productService.addProduct(product);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    /**
     * Ajouter plusieurs produits en une seule requete.
     * Body: [ { ... }, { ... } ]
     */
    @PostMapping("/batch")
    public ResponseEntity<List<Product>> addProducts(@RequestBody List<Product> products) {
        List<Product> savedList = productService.addProducts(products);
        return new ResponseEntity<>(savedList, HttpStatus.CREATED);
    }

    /**
     * Obtenir tous les produits.
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * Obtenir un produit par son ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
}
