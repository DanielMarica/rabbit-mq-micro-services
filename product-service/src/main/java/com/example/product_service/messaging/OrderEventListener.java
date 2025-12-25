package com.example.product_service.messaging;

import com.example.product_service.messaging.events.OrderCreatedEvent;
import com.example.product_service.messaging.events.StockReservedEvent;
import com.example.product_service.messaging.events.StockRejectedEvent;
import com.example.product_service.service.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

  @Autowired
  private ProductService productService;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
  public void handleOrderCreated(OrderCreatedEvent event) {
    System.out.println("📦 Received order: " + event.getOrderId()
        + " for product: " + event.getProductId()
        + " quantity: " + event.getQuantity());

    boolean reserved = productService.reserveStock(
        event.getProductId(),
        event.getQuantity()
    );

    if (reserved) {
      System.out.println("✅ Stock reserved successfully");
      StockReservedEvent stockEvent = new StockReservedEvent(
          event.getOrderId(),
          event.getProductId(),
          event.getQuantity()
      );
      System.out.println("📤 Sending: " + stockEvent);
      rabbitTemplate.convertAndSend(
          RabbitMQConfig.STOCK_EXCHANGE,
          RabbitMQConfig.STOCK_RESERVED_KEY,
          stockEvent
      );
    } else {
      System.out.println("❌ Stock reservation failed");
      StockRejectedEvent rejectEvent = new StockRejectedEvent(
          event.getOrderId(),
          event.getProductId(),
          event.getQuantity(),
          "Insufficient stock"
      );
      System.out.println("📤 Sending: " + rejectEvent);
      rabbitTemplate.convertAndSend(
          RabbitMQConfig.STOCK_EXCHANGE,
          RabbitMQConfig.STOCK_REJECTED_KEY,
          rejectEvent
      );
    }
  }
}
