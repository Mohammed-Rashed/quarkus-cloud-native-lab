package com.mohammed.order.messaging.event;

import java.util.UUID;

public record OrderCreatedEvent(
        UUID eventId,
        Long orderId,
        String product,
        int quantity,
        String status
) {
}
