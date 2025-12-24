package com.example.apigateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * Gateway Controller - Routeur principal de l'API Gateway
 *
 * Ce controller reçoit toutes les requêtes des clients et les redirige
 * vers les microservices appropriés (Customer, Product, Order).
 *
 * Pattern : API Gateway (point d'entrée unique)
 */
@RestController  // ← Indique que cette classe gère des requêtes HTTP et retourne du JSON
@RequestMapping("/api")  // ← Préfixe : toutes les routes de ce controller commencent par /api
public class GatewayController {

  // ============ CONFIGURATION DES URLS DES MICROSERVICES ============

  /**
   * URL du Customer Service
   * Valeur lue depuis application.properties : customer.service.url=http://localhost:8082
   */
  @Value("${customer.service.url}")  // ← Injecte la valeur depuis application.properties
  private String customerServiceUrl;

  /**
   * URL du Product Service
   * Valeur lue depuis application.properties : product.service.url=http://localhost:8081
   */
  @Value("${product.service.url}")
  private String productServiceUrl;

  /**
   * URL du Order Service
   * Valeur lue depuis application.properties : order.service.url=http://localhost:8083
   */
  @Value("${order.service.url}")
  private String orderServiceUrl;

  /**
   * RestTemplate - Client HTTP pour appeler les autres microservices
   *
   * C'est l'outil qui permet à l'API Gateway de faire des requêtes HTTP
   * vers les autres services (GET, POST, DELETE, etc.)
   *
   * Analogie : C'est le "coursier" qui va chercher/déposer les données
   *            dans les autres microservices
   */
  private final RestTemplate restTemplate = new RestTemplate();

  // ============ ROUTES CUSTOMERS ============

  /**
   * GET /api/customers - Récupérer tous les clients
   *
   * Flux : Client → Gateway (8080) → Customer Service (8082) → Gateway → Client
   *
   * @return Liste de tous les customers (JSON)
   */
  @GetMapping("/customers")  // ← Répond aux GET sur /api/customers
  public Object getCustomers() {
    // RestTemplate appelle : GET http://localhost:8082/customers
    // Retourne le résultat tel quel au client
    return restTemplate.getForObject(customerServiceUrl + "/customers", Object.class);
  }

  /**
   * GET /api/customers/{id} - Récupérer un client par son ID
   *
   * Exemple : GET /api/customers/1
   *
   * @param id L'ID du customer à récupérer
   * @return Le customer correspondant (JSON)
   */
  @GetMapping("/customers/{id}")  // ← {id} est une variable d'URL
  public Object getCustomerById(@PathVariable Long id) {  // ← @PathVariable récupère {id} depuis l'URL
    // Appelle : GET http://localhost:8082/customers/1
    return restTemplate.getForObject(customerServiceUrl + "/customers/" + id, Object.class);
  }

  /**
   * POST /api/customers - Créer un nouveau client
   *
   * Exemple de body JSON :
   * {
   *   "name": "Jean Dupont",
   *   "address": "123 Rue de Paris"
   * }
   *
   * @param body Le customer à créer (au format JSON)
   * @return Le customer créé avec son ID (JSON)
   */
  @PostMapping("/customers")  // ← Répond aux POST sur /api/customers
  public Object createCustomer(@RequestBody Object body) {  // ← @RequestBody récupère le JSON du body
    // Préparation des headers HTTP
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);  // ← Dit au service : "J'envoie du JSON"

    // Création de l'entité HTTP (body + headers)
    HttpEntity<Object> entity = new HttpEntity<>(body, headers);

    // Appelle : POST http://localhost:8082/customers avec le body JSON
    return restTemplate.postForObject(customerServiceUrl + "/customers", entity, Object.class);
  }

  /**
   * DELETE /api/customers/{id} - Supprimer un client
   *
   * Exemple : DELETE /api/customers/1
   *
   * @param id L'ID du customer à supprimer
   * @return 204 No Content (succès sans body)
   */
  @DeleteMapping("/customers/{id}")  // ← Répond aux DELETE sur /api/customers/{id}
  public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
    // Appelle : DELETE http://localhost:8082/customers/1
    restTemplate.delete(customerServiceUrl + "/customers/" + id);

    // Retourne un code HTTP 204 (No Content) = succès sans réponse
    return ResponseEntity.noContent().build();
  }

  // ============ ROUTES PRODUCTS ============
  // (Même logique que pour les customers)

  @GetMapping("/products")
  public Object getProducts() {
    return restTemplate.getForObject(productServiceUrl + "/products", Object.class);
  }

  @GetMapping("/products/{id}")
  public Object getProductById(@PathVariable Long id) {
    return restTemplate.getForObject(productServiceUrl + "/products/" + id, Object.class);
  }

  @PostMapping("/products")
  public Object createProduct(@RequestBody Object body) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<Object> entity = new HttpEntity<>(body, headers);
    return restTemplate.postForObject(productServiceUrl + "/products", entity, Object.class);
  }

  // ============ ROUTES ORDERS ============
  // (Même logique que pour les customers et products)

  @GetMapping("/orders")
  public Object getOrders() {
    return restTemplate.getForObject(orderServiceUrl + "/orders", Object.class);
  }

  @GetMapping("/orders/{id}")
  public Object getOrderById(@PathVariable Long id) {
    return restTemplate.getForObject(orderServiceUrl + "/orders/" + id, Object.class);
  }

  @PostMapping("/orders")
  public Object createOrder(@RequestBody Object body) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<Object> entity = new HttpEntity<>(body, headers);
    return restTemplate.postForObject(orderServiceUrl + "/orders", entity, Object.class);
  }
}