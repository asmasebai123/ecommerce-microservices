package com.ecommerce.cartservice.dto;

/**
 * DTO representant le message recu depuis ActiveMQ (product-queue).
 * Correspond a la serialisation JSON de l'entite Product du product-service.
 */
public class ProductMessage {

    private Long id;
    private String name;
    private Double price;
    private String description;

    public ProductMessage() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ProductMessage{id=" + id + ", name='" + name + "', price=" + price + "}";
    }
}
