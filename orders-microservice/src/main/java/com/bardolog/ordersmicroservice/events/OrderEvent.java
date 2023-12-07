package com.bardolog.ordersmicroservice.events;

import com.bardolog.ordersmicroservice.model.enums.OrderStatus;

public record OrderEvent(String orderNumbre, int  itemsCount, OrderStatus orderStatus) {
}
