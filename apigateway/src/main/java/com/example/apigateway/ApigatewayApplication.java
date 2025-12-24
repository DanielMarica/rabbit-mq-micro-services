package com.example.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale de l'API Gateway
 *
 * Cette application sert de point d'entrée unique pour tous les microservices.
 * Elle route les requêtes des clients vers les bons microservices (Customer, Product, Order).
 */
@SpringBootApplication  // ← Annotation magique Spring Boot qui active :
//   1. @Configuration : Cette classe peut définir des beans
//   2. @EnableAutoConfiguration : Spring Boot configure automatiquement l'application
//   3. @ComponentScan : Spring scanne ce package et ses sous-packages pour trouver les @RestController, @Service, etc.
public class ApigatewayApplication {

	/**
	 * Méthode main - Point d'entrée de l'application
	 *
	 * @param args Arguments de la ligne de commande (non utilisés ici)
	 */
	public static void main(String[] args) {
		// SpringApplication.run() démarre l'application Spring Boot :
		// 1. Crée le contexte Spring (conteneur de beans)
		// 2. Scan les composants (@RestController, @Service, etc.)
		// 3. Démarre le serveur Tomcat embarqué sur le port configuré (8080)
		// 4. Initialise tous les beans et injecte les dépendances
		SpringApplication.run(ApigatewayApplication.class, args);

		// Une fois cette ligne exécutée, l'API Gateway est opérationnelle
		// et écoute les requêtes HTTP sur http://localhost:8080
	}

}