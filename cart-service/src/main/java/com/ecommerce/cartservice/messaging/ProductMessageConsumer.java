package com.ecommerce.cartservice.messaging;

import com.ecommerce.cartservice.dto.ProductMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Consommateur de messages JMS / ActiveMQ.
 * Ecoute la file "product-queue" et traite chaque nouveau produit cree
 * par le product-service.
 *
 * Dans un cas reel, on pourrait ici :
 *   - mettre a jour les prix dans le panier si un produit change de prix
 *   - alimenter un cache local de produits disponibles
 *   - envoyer une notification a l'utilisateur
 */
@Component
public class ProductMessageConsumer {

    private final ObjectMapper objectMapper;

    public ProductMessageConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Methode declenchee automatiquement a chaque message dans "product-queue".
     *
     * @param message le JSON du produit envoye par product-service
     */
    @JmsListener(destination = "product-queue")
    public void receiveProductMessage(String message) {
        try {
            ProductMessage product = objectMapper.readValue(message, ProductMessage.class);
            System.out.println("📥 [cart-service] Message recu depuis product-queue : " + product);
            System.out.println("   → Produit disponible : '"
                    + product.getName() + "' au prix de " + product.getPrice() + " €");
            // Ici on pourrait mettre a jour un cache ou envoyer une notification
        } catch (JsonProcessingException e) {
            System.err.println("❌ [cart-service] Erreur de deserialisation : " + e.getMessage());
        }
    }
}
