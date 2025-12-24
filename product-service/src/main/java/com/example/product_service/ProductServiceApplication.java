package com.example.product_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ProductServiceApplication - Classe principale du Product Service
 * 
 * Cette classe démarre l'application Spring Boot du Product Service.
 * 
 * @SpringBootApplication combine 3 annotations :
 * - @Configuration : Indique que c'est une classe de configuration Spring
 * - @EnableAutoConfiguration : Active la configuration automatique de Spring Boot
 * - @ComponentScan : Scanne les packages pour trouver les @Component, @Service, @Repository, etc.
 */
@SpringBootApplication  // ← Annotation principale Spring Boot
public class ProductServiceApplication {

    /**
     * Méthode main - Point d'entrée de l'application
     * 
     * SpringApplication.run() :
     * 1. Démarre le serveur web intégré (Tomcat)
     * 2. Initialise le contexte Spring
     * 3. Scanne et enregistre tous les beans (@Service, @Repository, @Controller)
     * 4. Configure la base de données H2
     * 5. Démarre l'application sur le port configuré (8081)
     * 
     * @param args Arguments de ligne de commande (non utilisés ici)
     */
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
        
        System.out.println("================================================");
        System.out.println("🚀 Product Service démarré avec succès !");
        System.out.println("📍 Port : 8081");
        System.out.println("🗄️  Base de données : H2 (en mémoire)");
        System.out.println("🌐 Console H2 : http://localhost:8081/h2-console");
        System.out.println("📡 API : http://localhost:8081/products");
        System.out.println("================================================");
    }
}
