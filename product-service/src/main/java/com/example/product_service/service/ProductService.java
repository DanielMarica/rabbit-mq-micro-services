package com.example.product_service.service;

import com.example.product_service.model.Product;
import com.example.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Récupérer tous les produits
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Récupérer un produit par ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Créer un nouveau produit
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // Supprimer un produit
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Vérifier le stock
    public boolean checkStock(Long id, Integer quantity) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        return product.getStock() >= quantity;
    }
    // Réserver du stock
    public boolean reserveStock(Long id, Integer quantity) {
        try {
            Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

            // Vérifier si le stock est suffisant
            if (product.getStock() < quantity) {
                return false;  // Stock insuffisant
            }

            // Réduire le stock
            product.setStock(product.getStock() - quantity);
            productRepository.save(product);

            System.out.println("✅ Stock reserved: " + quantity + " units for product " + id);
            return true;  // Réservation réussie
        } catch (Exception e) {
            System.err.println("❌ Error reserving stock: " + e.getMessage());
            return false;
        }
    }
}