package com.example.product_service.messaging;

import com.example.product_service.messaging.events.StockReservedEvent;
import com.example.product_service.messaging.events.StockRejectedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockEventPublisher {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  // Publier StockReserved (stock OK)
  public void publishStockReserved(StockReservedEvent event) {
    System.out.println("📤 Publishing StockReservedEvent: " + event);

    rabbitTemplate.convertAndSend(
        RabbitMQConfig.STOCK_EXCHANGE,        // Exchange
        RabbitMQConfig.STOCK_RESERVED_KEY,    // Routing key
        event                                  // Message
    );

    System.out.println("✅ StockReservedEvent published successfully!");
  }

  // Publier StockRejected (stock insuffisant)
  public void publishStockRejected(StockRejectedEvent event) {
    System.out.println("📤 Publishing StockRejectedEvent: " + event);

    rabbitTemplate.convertAndSend(
        RabbitMQConfig.STOCK_EXCHANGE,        // Exchange
        RabbitMQConfig.STOCK_REJECTED_KEY,    // Routing key
        event                                  // Message
    );

    System.out.println("✅ StockRejectedEvent published successfully!");
  }
}
