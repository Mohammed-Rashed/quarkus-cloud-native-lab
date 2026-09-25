package com.mohammed.order.messaging;

import com.mohammed.order.messaging.event.OrderCreatedEvent;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class OrderEventProducer {

    @Channel("order-events-out")
    Emitter<OrderCreatedEvent> emitter;
    public void send(OrderCreatedEvent message) {
        System.out.println("Sending Kafka event: " + message);
        emitter.send(message);
    }
}
