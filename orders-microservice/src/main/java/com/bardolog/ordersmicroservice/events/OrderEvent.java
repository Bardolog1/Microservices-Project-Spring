package com.bardolog.ordersmicroservice.events;

import com.bardolog.ordersmicroservice.model.enums.OrderStatus;

public record OrderEvent(String orderNumber, int  itemsCount, OrderStatus orderStatus) {
}
