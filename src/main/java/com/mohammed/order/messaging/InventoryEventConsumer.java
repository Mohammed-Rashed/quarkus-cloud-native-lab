package com.mohammed.order.messaging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohammed.order.OrderService;
import com.mohammed.order.messaging.event.OrderCreatedEvent;
import com.mohammed.order.messaging.event.StockRejectedEvent;
import com.mohammed.order.messaging.event.StockReservedEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class InventoryEventConsumer {
    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    public InventoryEventConsumer(OrderService orderService,ObjectMapper objectMapper) {
        this.orderService = orderService;
        this.objectMapper = objectMapper;
    }
    @Transactional
    @Incoming("inventory-events-in")
    public void consume(String message) throws Exception  {
        JsonNode json = objectMapper.readTree(message);
        String eventType = json.get("eventType").asText();
        if ("STOCK_RESERVED".equals(eventType)) {

            StockReservedEvent event = objectMapper.readValue(
                    message,
                    StockReservedEvent.class
            );

            orderService.confirmOrder(event.orderId());

        }else if ("STOCK_REJECTED".equals(eventType)) {

            StockRejectedEvent event = objectMapper.readValue(
                    message,
                    StockRejectedEvent.class
            );

            orderService.rejectOrder(event.orderId());

            System.out.println(
                    "Order rejected: " + event.orderId()
                            + ", reason: " + event.reason()
            );
        }

    }
}
