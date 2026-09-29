package com.mohammed.order.messaging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohammed.order.OrderService;
import com.mohammed.order.messaging.event.OrderCreatedEvent;
import com.mohammed.order.messaging.event.StockRejectedEvent;
import com.mohammed.order.messaging.event.StockReservedEvent;
import com.mohammed.order.repository.ProcessedEventRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.util.UUID;

@ApplicationScoped
public class InventoryEventConsumer {
    private final OrderService orderService;
    private final ObjectMapper objectMapper;
    private final ProcessedEventRepository processedEventRepository;

    public InventoryEventConsumer(
            OrderService orderService,
            ObjectMapper objectMapper,
            ProcessedEventRepository  processedEventRepository
            ) {
        this.orderService = orderService;
        this.objectMapper = objectMapper;
        this.processedEventRepository = processedEventRepository;
    }
    @Transactional
    @Incoming("inventory-events-in")
    public void consume(String message) throws Exception  {
        JsonNode json = objectMapper.readTree(message);
        String eventType = json.get("eventType").asText();
        UUID eventId = UUID.fromString(
                json.get("eventId").asText()
        );
        if (processedEventRepository.isProcessed(eventId)) {

            System.out.println(
                    "Duplicate event detected, skipping: " + eventId
            );

            return;
        }
        if ("STOCK_RESERVED".equals(eventType)) {

            StockReservedEvent event = objectMapper.readValue(
                    message,
                    StockReservedEvent.class
            );

            orderService.confirmOrder(event.orderId());

            processedEventRepository.markAsProcessed(event.eventId());

        }
        else if ("STOCK_REJECTED".equals(eventType)) {

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
