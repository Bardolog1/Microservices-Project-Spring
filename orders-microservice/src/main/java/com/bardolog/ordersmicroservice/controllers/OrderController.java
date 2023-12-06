package com.bardolog.ordersmicroservice.controllers;

import com.bardolog.ordersmicroservice.model.dtos.OrderRequest;
import com.bardolog.ordersmicroservice.model.dtos.OrdersResponse;
import com.bardolog.ordersmicroservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        this.orderService.placeOrder(orderRequest);
        return "Order placed successfully";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrdersResponse> getOrders(){
        return this.orderService.getAllOrders();
    }

}
