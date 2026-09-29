package com.mohammed.order.messaging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohammed.order.OrderService;
import com.mohammed.order.messaging.event.PaymentCompletedEvent;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;

public class PaymentEventConsumer {
    private final ObjectMapper objectMapper;
    private final OrderService orderService;
    public PaymentEventConsumer(
            ObjectMapper objectMapper,
            OrderService orderService
    ) {
        this.objectMapper = objectMapper;
        this.orderService = orderService;
    }

    @Incoming("payment-events-in")
    @Transactional
    public void consume(String message) throws Exception {

        JsonNode json = objectMapper.readTree(message);

        String eventType = json.get("eventType").asText();

        if (!"PAYMENT_COMPLETED".equals(eventType)) {
            return;
        }

        PaymentCompletedEvent event = objectMapper.readValue(
                message,
                PaymentCompletedEvent.class
        );

        orderService.confirmOrder(event.orderId());

        System.out.println(
                "Order confirmed after payment: " + event.orderId()
        );
    }
}
