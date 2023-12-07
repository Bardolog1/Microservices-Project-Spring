package com.bardolog.ordersmicroservice.controllers;

import com.bardolog.ordersmicroservice.model.dtos.OrderRequest;
import com.bardolog.ordersmicroservice.model.dtos.OrdersResponse;
import com.bardolog.ordersmicroservice.services.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name="order-microservice", fallbackMethod = "placeOrderFallback")
    public ResponseEntity<OrdersResponse> placeOrder(@RequestBody OrderRequest orderRequest){
        var orders = this.orderService.placeOrder(orderRequest);
        return ResponseEntity.ok(orders);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrdersResponse> getOrders(){
        return this.orderService.getAllOrders();
    }

    public ResponseEntity<OrdersResponse> placeOrderFallback(OrderRequest orderRequest, Throwable throwable){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }



}
