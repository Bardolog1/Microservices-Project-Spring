package com.bardolog.notification_service.events;


import com.bardolog.notification_service.model.enums.OrderStatus;

public record OrderEvent(String orderNumber, int  itemsCount, OrderStatus orderStatus) {
}
