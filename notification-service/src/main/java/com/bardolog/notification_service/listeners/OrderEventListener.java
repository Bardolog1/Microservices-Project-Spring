package com.bardolog.notification_service.listeners;

import com.bardolog.notification_service.events.OrderEvent;
import com.bardolog.notification_service.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderEventListener {

    @KafkaListener(topics = "orders-events")
    public void handleOrdersNotifications(String message){
        var orderEvent = JsonUtil.fromJson(message, OrderEvent.class);

        // Send email to customer, send SMS to customer, etc
        //Notify, another service..

        log.info("Order {} event received for order : {} with {} items", orderEvent.orderStatus(), orderEvent.orderNumber(), orderEvent.itemsCount() );
    }

}
