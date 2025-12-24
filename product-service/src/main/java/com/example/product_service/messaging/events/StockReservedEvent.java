package com.example.order_service.messaging.events;

import java.io.Serializable;

public class StockReservedEvent implements Serializable {

  private Long orderId;
  private Long productId;
  private Integer quantity;
  private boolean success;

  public StockReservedEvent() {
  }

  public StockReservedEvent(Long orderId, Long productId, Integer quantity, boolean success) {
    this.orderId = orderId;
    this.productId = productId;
    this.quantity = quantity;
    this.success = success;
  }

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

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  @Override
  public String toString() {
    return "StockReservedEvent{" +
        "orderId=" + orderId +
        ", productId=" + productId +
        ", quantity=" + quantity +
        ", success=" + success +
        '}';
  }
}