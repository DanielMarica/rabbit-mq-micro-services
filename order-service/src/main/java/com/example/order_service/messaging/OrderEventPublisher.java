package com.example.order_service.messaging;

import com.example.order_service.messaging.events.OrderCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderEventPublisher {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  // Publier l'événement OrderCreated
  public void publishOrderCreated(OrderCreatedEvent event) {
    System.out.println("📤 Publishing OrderCreatedEvent: " + event);

    rabbitTemplate.convertAndSend(
        RabbitMQConfig.ORDER_EXCHANGE,      // Exchange
        RabbitMQConfig.ORDER_ROUTING_KEY,   // Routing key
        event                                // Message (l'événement)
    );

    System.out.println("✅ OrderCreatedEvent published successfully!");
  }
}