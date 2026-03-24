package com.ecommerce.productservice.service;

import com.ecommerce.productservice.messaging.ProductMessageProducer;
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Couche metier du product-service.
 * Gere la persistence et la notification via ActiveMQ.
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMessageProducer messageProducer;

    public ProductService(ProductRepository productRepository,
                          ProductMessageProducer messageProducer) {
        this.productRepository = productRepository;
        this.messageProducer = messageProducer;
    }

    /**
     * Sauvegarde un produit et notifie cart-service via ActiveMQ.
     */
    public Product addProduct(Product product) {
        Product saved = productRepository.save(product);
        messageProducer.sendProductMessage(saved);
        return saved;
    }

    /**
     * Sauvegarde plusieurs produits en lot et notifie pour chacun.
     */
    public List<Product> addProducts(List<Product> products) {
        List<Product> savedList = productRepository.saveAll(products);
        savedList.forEach(messageProducer::sendProductMessage);
        return savedList;
    }

    /**
     * Retourne tous les produits.
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Retourne un produit par son ID ou lance une exception.
     */
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable avec l'id : " + id));
    }
}
