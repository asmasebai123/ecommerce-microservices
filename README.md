# 🏗️ E-Commerce Microservices — Spring Boot

Projet complet de microservices e-commerce avec Spring Boot, ActiveMQ, API Gateway et Eureka.

---

## 📦 Architecture

```
                        ┌──────────────┐
                        │   Client     │
                        │  (Postman)   │
                        └──────┬───────┘
                               │
                        ┌──────▼───────┐
                        │  API Gateway │
                        │  (port 8080) │
                        └──┬───────┬───┘
                           │       │
              ┌────────────▼─┐   ┌─▼────────────┐
              │product-service│   │ cart-service  │
              │  (port 8081)  │   │  (port 8082)  │
              │   [H2 DB]    │   │   [H2 DB]     │
              └──────┬───────┘   └───────▲───────┘
                     │                   │
                     │   ┌───────────┐   │
                     └──►│ ActiveMQ  ├───┘
                         │(port 61616│
                         └───────────┘
                               ▲
                        ┌──────┴───────┐
                        │Eureka Server │
                        │  (port 8761) │
                        └──────────────┘
```

## 🗂️ Modules

| Module           | Port | Description                              |
|------------------|------|------------------------------------------|
| `eureka-server`  | 8761 | Registre de services (Service Discovery) |
| `api-gateway`    | 8080 | Point d'entrée unique (routage)          |
| `product-service`| 8081 | CRUD produits + H2 `productdb`           |
| `cart-service`   | 8082 | CRUD panier + H2 `cartdb`                |

---

## ⚙️ Prérequis

- **Java 17+**
- **Maven 3.8+**
- **IntelliJ IDEA** (recommandé) ou Eclipse
- **Apache ActiveMQ Classic** (téléchargement ci-dessous)
- **Postman** (pour les tests)

---

## 🚀 Démarrage

### 1. Installer et lancer ActiveMQ

Télécharger : https://activemq.apache.org/components/classic/download/

```bash
# macOS / Linux
./bin/activemq start

# Windows
bin\win64\activemq.bat start
```

Ou avec Docker :
```bash
docker run -d --name activemq -p 61616:61616 -p 8161:8161 apache/activemq-classic:latest
```

Console ActiveMQ : http://localhost:8161 (admin / admin)

---

### 2. Ouvrir le projet dans IntelliJ

1. **File → Open** → sélectionner le dossier `ecommerce-microservices`
2. Ouvrir chaque module comme projet Maven séparé, **ou** créer un projet multi-module
3. IntelliJ détectera les 4 `pom.xml` automatiquement

---

### 3. Lancer les services dans cet ordre

```
1. eureka-server        → EurekaServerApplication.java
2. product-service      → ProductServiceApplication.java
3. cart-service         → CartServiceApplication.java
4. api-gateway          → ApiGatewayApplication.java
```

Vérifier Eureka : http://localhost:8761
→ Les 3 services doivent apparaître comme **UP**

---

## 🧪 Tests avec Postman

Tous les appels passent par le **Gateway (port 8080)**.

### Produits

```
# Ajouter un produit
POST http://localhost:8080/api/products
Content-Type: application/json
{
    "name": "Laptop HP",
    "price": 1299.99,
    "description": "HP Pavilion 15 - 16GB RAM"
}

# Ajouter plusieurs produits
POST http://localhost:8080/api/products/batch
Content-Type: application/json
[
    { "name": "iPhone 15", "price": 999.99, "description": "Apple iPhone 15 Pro" },
    { "name": "Samsung Galaxy S24", "price": 899.99, "description": "Samsung flagship" },
    { "name": "AirPods Pro", "price": 249.99, "description": "Wireless earbuds" }
]

# Lister tous les produits
GET http://localhost:8080/api/products

# Obtenir un produit par ID
GET http://localhost:8080/api/products/1
```

### Panier

```
# Ajouter au panier
POST http://localhost:8080/api/cart
Content-Type: application/json
{
    "productId": 1,
    "productName": "Laptop HP",
    "productPrice": 1299.99,
    "quantity": 1
}

# Voir le panier
GET http://localhost:8080/api/cart

# Supprimer un produit du panier
DELETE http://localhost:8080/api/cart/1

# Vider le panier
DELETE http://localhost:8080/api/cart
```

---

## 🔍 Consoles de supervision

| URL                              | Description              |
|----------------------------------|--------------------------|
| http://localhost:8761            | Eureka Dashboard         |
| http://localhost:8161            | ActiveMQ Console         |
| http://localhost:8081/h2-console | H2 Console product-service |
| http://localhost:8082/h2-console | H2 Console cart-service  |

Pour H2 Console, utiliser :
- JDBC URL : `jdbc:h2:mem:productdb` (ou `cartdb`)
- User : `sa` / Password : *(vide)*

---

## 🏛️ Concepts illustrés

| Concept              | Implémentation                              |
|----------------------|---------------------------------------------|
| Database per Service | H2 `productdb` ≠ `cartdb`                  |
| Async Messaging      | ActiveMQ `product-queue`                    |
| API Gateway          | Spring Cloud Gateway MVC                    |
| Service Discovery    | Netflix Eureka                              |
| Load Balancing       | `lb://service-name` dans la config gateway  |
