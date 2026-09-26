package com.mohammed.order.messaging;

import com.mohammed.order.messaging.event.OrderCreatedEvent;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class OrderCreatedEventDeserializer extends ObjectMapperDeserializer<OrderCreatedEvent> {
    public OrderCreatedEventDeserializer() {
        super(OrderCreatedEvent.class);
    }
}
