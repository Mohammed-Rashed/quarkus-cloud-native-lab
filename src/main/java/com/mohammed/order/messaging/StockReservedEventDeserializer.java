package com.mohammed.order.messaging;

import com.mohammed.order.messaging.event.StockReservedEvent;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class StockReservedEventDeserializer
        extends ObjectMapperDeserializer<StockReservedEvent> {

    public StockReservedEventDeserializer() {
        super(StockReservedEvent.class);
    }
}