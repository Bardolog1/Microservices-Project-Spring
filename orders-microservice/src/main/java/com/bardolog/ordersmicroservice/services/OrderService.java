package com.bardolog.ordersmicroservice.services;


import com.bardolog.ordersmicroservice.events.OrderEvent;
import com.bardolog.ordersmicroservice.model.dtos.BaseResponse;
import com.bardolog.ordersmicroservice.model.dtos.OrderItemRequest;
import com.bardolog.ordersmicroservice.model.dtos.OrderRequest;
import com.bardolog.ordersmicroservice.model.dtos.OrdersResponse;
import com.bardolog.ordersmicroservice.model.entities.OrderEntity;
import com.bardolog.ordersmicroservice.model.entities.OrderItemEntity;
import com.bardolog.ordersmicroservice.model.enums.OrderStatus;
import com.bardolog.ordersmicroservice.repositories.OrderRepository;
import com.bardolog.ordersmicroservice.utils.JsonUtil;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObservationRegistry observationRegistry;

    public OrdersResponse placeOrder(OrderRequest orderRequest){

        Observation inventoryServiceObservation = Observation.createNotStarted("inventory-microservice", observationRegistry);

        //Check for inventory
       return inventoryServiceObservation.observe(()->{
          BaseResponse result =  this.webClientBuilder.build()
                  .post()
                  //.uri("http://localhost:8080/api/inventory/in-stock")
                  .uri("lb://inventory-microservice/api/inventory/in-stock")
                  .bodyValue(orderRequest.getOrderItems())
                  .retrieve()
                  .bodyToMono(BaseResponse.class)
                  .block();

          if(result != null && !result.hasErrors()) {

              OrderEntity order = new OrderEntity();
              order.setOrderNumber(UUID.randomUUID().toString());
              order.setOrderItems(orderRequest.getOrderItems().stream().map(oR -> mapOrderItemRequestToOrderItem(oR, order)).toList());
              var saverOrder = this.orderRepository.save(order);

              //TODO: Send message  to order topic
              this.kafkaTemplate.send("orders-events", JsonUtil.toJson(
                      new OrderEvent(saverOrder.getOrderNumber(), saverOrder.getOrderItems().size(), OrderStatus.PLACED)
              ));

              return mapToOrderResponse(saverOrder);
          }else {
              throw new IllegalArgumentException("Some of the products are not in stock");
          }
      });
    }

    public List<OrdersResponse> getAllOrders(){
        List<OrderEntity> orders = this.orderRepository.findAll();
        return orders.stream().map(this::mapToOrderResponse).toList();
    }

    private OrdersResponse mapToOrderResponse(OrderEntity oE){
        return new OrdersResponse(oE.getId(),oE.getOrderNumber(),oE.getOrderItems().stream().map(this::mapToItemRequest).toList());
    }

    private OrderItemRequest mapToItemRequest(OrderItemEntity orderItemEntity) {
        return new OrderItemRequest(orderItemEntity.getId(), orderItemEntity.getSku(), orderItemEntity.getPrice(), orderItemEntity.getQuantity());
    }


    private OrderItemEntity mapOrderItemRequestToOrderItem(OrderItemRequest oR, OrderEntity oE){
        return OrderItemEntity.builder()
                .id(oR.getId())
                .sku(oR.getSku())
                .price(oR.getPrice())
                .quantity(oR.getQuantity())
                .order(oE)
                .build();
    }
}
