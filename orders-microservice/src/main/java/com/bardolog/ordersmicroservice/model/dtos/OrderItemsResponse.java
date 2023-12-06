package com.bardolog.ordersmicroservice.model.dtos;


public record OrderItemsResponse(Long id, String sku, Double price, Long quantity) {
}
