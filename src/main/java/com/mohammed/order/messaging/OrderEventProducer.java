package com.mohammed.order.messaging;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class OrderEventProducer {

    @Channel("order-events-out")
    Emitter<String> emitter;
    public void send(String message) {
        System.out.println("Sending Kafka event: " + message);
        emitter.send(message);
    }
}
