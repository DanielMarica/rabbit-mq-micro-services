package com.example.product_service.messaging;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;  // ⭐ Changé ici
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  public static final String ORDER_QUEUE = "order.created.queue";
  public static final String ORDER_EXCHANGE = "order.exchange";
  public static final String ORDER_ROUTING_KEY = "order.created";

  public static final String STOCK_RESERVED_QUEUE = "stock.reserved.queue";
  public static final String STOCK_REJECTED_QUEUE = "stock.rejected.queue";

  public static final String STOCK_EXCHANGE = "stock.exchange";
  public static final String STOCK_RESERVED_KEY = "stock.reserved";
  public static final String STOCK_REJECTED_KEY = "stock.rejected";

  // ⭐ Utiliser JacksonJsonMessageConverter (Jackson 3)
  @Bean
  public MessageConverter jsonMessageConverter() {
    return new JacksonJsonMessageConverter();
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setMessageConverter(jsonMessageConverter());
    return template;
  }

  @Bean
  public Queue orderQueue() {
    return new Queue(ORDER_QUEUE, true);
  }

  @Bean
  public TopicExchange orderExchange() {
    return new TopicExchange(ORDER_EXCHANGE);
  }

  @Bean
  public Binding orderBinding(Queue orderQueue, TopicExchange orderExchange) {
    return BindingBuilder.bind(orderQueue).to(orderExchange).with(ORDER_ROUTING_KEY);
  }

  @Bean
  public Queue stockReservedQueue() {
    return new Queue(STOCK_RESERVED_QUEUE, true);
  }

  @Bean
  public Queue stockRejectedQueue() {
    return new Queue(STOCK_REJECTED_QUEUE, true);
  }

  @Bean
  public TopicExchange stockExchange() {
    return new TopicExchange(STOCK_EXCHANGE);
  }

  @Bean
  public Binding stockReservedBinding(Queue stockReservedQueue, TopicExchange stockExchange) {
    return BindingBuilder.bind(stockReservedQueue).to(stockExchange).with(STOCK_RESERVED_KEY);
  }

  @Bean
  public Binding stockRejectedBinding(Queue stockRejectedQueue, TopicExchange stockExchange) {
    return BindingBuilder.bind(stockRejectedQueue).to(stockExchange).with(STOCK_REJECTED_KEY);
  }
}
