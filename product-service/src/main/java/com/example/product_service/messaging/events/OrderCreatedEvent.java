package com.example.order_service.messaging.events;

import java.io.Serializable;

public class OrderCreatedEvent implements Serializable {

  private Long orderId;
  private Long productId;
  private Integer quantity;
  private Long customerId;

  // Constructeur par défaut (requis pour la désérialisation)
  public OrderCreatedEvent() {
  }

  // Constructeur avec paramètres
  public OrderCreatedEvent(Long orderId, Long productId, Integer quantity, Long customerId) {
    this.orderId = orderId;
    this.productId = productId;
    this.quantity = quantity;
    this.customerId = customerId;
  }

  // Getters et Setters
  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  @Override
  public String toString() {
    return "OrderCreatedEvent{" +
        "orderId=" + orderId +
        ", productId=" + productId +
        ", quantity=" + quantity +
        ", customerId=" + customerId +
        '}';
  }
}