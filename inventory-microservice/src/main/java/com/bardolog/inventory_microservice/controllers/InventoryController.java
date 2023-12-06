package com.bardolog.inventory_microservice.controllers;

import com.bardolog.inventory_microservice.model.dtos.BaseResponse;
import com.bardolog.inventory_microservice.model.dtos.OrderItemRequest;
import com.bardolog.inventory_microservice.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/{sku}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("sku")String sku){
        return inventoryService.isInStock(sku);
    }

    @PostMapping("/in-stock")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse araInStock(@RequestBody List<OrderItemRequest> orderItemRequests){
        return inventoryService.areInStock(orderItemRequests);
    }

}
