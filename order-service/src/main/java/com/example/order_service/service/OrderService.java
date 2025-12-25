package com.example.order_service.service;

import com.example.order_service.messaging.OrderEventPublisher;
import com.example.order_service.messaging.events.OrderCreatedEvent;
import com.example.order_service.model.Order;
import com.example.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderEventPublisher orderEventPublisher;  // ⭐ NOUVEAU

    // Récupérer toutes les commandes
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Récupérer une commande par ID
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // Récupérer les commandes d'un client
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    // ⭐ NOUVELLE VERSION ASYNCHRONE avec RabbitMQ
    public Order createOrder(Order order) {
        // Validation de base
        if (order.getCustomerId() == null) {
            throw new IllegalArgumentException("Customer ID est requis");
        }
        if (order.getProductId() == null) {
            throw new IllegalArgumentException("Product ID est requis");
        }
        if (order.getQuantity() == null || order.getQuantity() <= 0) {
            throw new IllegalArgumentException("La quantité doit être supérieure à 0");
        }

        // 1. Créer la commande avec statut PENDING
        order.setStatus("PENDING");
        Order savedOrder = orderRepository.save(order);

        System.out.println("📦 Order created with ID: " + savedOrder.getId() + " - Status: PENDING");

        // 2. Publier l'événement OrderCreated dans RabbitMQ
        OrderCreatedEvent event = new OrderCreatedEvent(
            savedOrder.getId(),
            savedOrder.getProductId(),
            savedOrder.getQuantity(),
            savedOrder.getCustomerId()
        );

        orderEventPublisher.publishOrderCreated(event);

        System.out.println("✅ OrderCreatedEvent published for order: " + savedOrder.getId());

        // 3. Retourner la commande (le statut sera mis à jour par le listener)
        return savedOrder;
    }

    // Supprimer une commande
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}