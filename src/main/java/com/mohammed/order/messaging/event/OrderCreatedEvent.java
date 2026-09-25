package com.mohammed.order.messaging.event;

public record OrderCreatedEvent(
        Long orderId,
        String product,
        int quantity,
        String status
) {
}
