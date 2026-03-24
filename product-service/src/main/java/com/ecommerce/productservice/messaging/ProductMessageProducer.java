package com.ecommerce.productservice.messaging;

import com.ecommerce.productservice.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Producteur de messages JMS / ActiveMQ.
 * Envoie un message JSON dans "product-queue" a chaque creation de produit.
 */
@Component
public class ProductMessageProducer {

    private static final String PRODUCT_QUEUE = "product-queue";

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public ProductMessageProducer(JmsTemplate jmsTemplate, ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Serialise le produit en JSON et l'envoie dans la file "product-queue".
     *
     * @param product le produit a envoyer
     */
    public void sendProductMessage(Product product) {
        try {
            String productJson = objectMapper.writeValueAsString(product);
            jmsTemplate.convertAndSend(PRODUCT_QUEUE, productJson);
            System.out.println("📤 [product-service] Message envoye dans product-queue : " + productJson);
        } catch (JsonProcessingException e) {
            System.err.println("❌ [product-service] Erreur de serialisation : " + e.getMessage());
        }
    }
}
