package com.bardolog.ordersmicroservice.model.dtos;

import java.util.List;

public record OrdersResponse(Long id, String orderNumber, List<OrderItemRequest> orderItems) {
}
