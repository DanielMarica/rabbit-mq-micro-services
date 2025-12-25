package com.example.order_service.messaging;

import com.example.order_service.messaging.events.StockRejectedEvent;
import com.example.order_service.messaging.events.StockReservedEvent;
import com.example.order_service.model.Order;
import com.example.order_service.repository.OrderRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockEventListener {

  @Autowired
  private OrderRepository orderRepository;

  // ⭐ Créer 2 queues séparées au lieu d'une seule!
  @RabbitListener(queues = "stock.reserved.queue")
  public void handleStockReserved(StockReservedEvent event) {
    System.out.println("📥 Received StockReservedEvent: " + event);
    try {
      Order order = orderRepository.findById(event.getOrderId())
          .orElseThrow(() -> new RuntimeException("Order not found: " + event.getOrderId()));

      order.setStatus("CONFIRMED");
      orderRepository.save(order);
      System.out.println("✅ Order " + event.getOrderId() + " status updated to CONFIRMED");
    } catch (Exception e) {
      System.err.println("❌ Error processing StockReservedEvent: " + e.getMessage());
    }
  }

  // ⭐ Queue différente pour les rejets!
  @RabbitListener(queues = "stock.rejected.queue")
  public void handleStockRejected(StockRejectedEvent event) {
    System.out.println("📥 Received StockRejectedEvent: " + event);
    try {
      Order order = orderRepository.findById(event.getOrderId())
          .orElseThrow(() -> new RuntimeException("Order not found: " + event.getOrderId()));

      order.setStatus("REJECTED");
      orderRepository.save(order);
      System.out.println("❌ Order " + event.getOrderId() + " status updated to REJECTED. Reason: " + event.getReason());
    } catch (Exception e) {
      System.err.println("❌ Error processing StockRejectedEvent: " + e.getMessage());
    }
  }
}
