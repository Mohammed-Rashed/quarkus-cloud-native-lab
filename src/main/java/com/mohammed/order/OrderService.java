package com.mohammed.order;

import com.mohammed.order.dto.CreateOrderDto;
import com.mohammed.order.dto.OrderResponseDto;
import com.mohammed.order.dto.PageResponseDto;
import com.mohammed.order.dto.UpdateOrderDto;
import com.mohammed.order.entity.OrderEntity;
import com.mohammed.order.exception.OrderNotFoundException;
import com.mohammed.order.messaging.OrderEventProducer;
import com.mohammed.order.messaging.event.OrderCreatedEvent;
import com.mohammed.order.repository.OrderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderEventProducer orderEventProducer;
    public OrderService(OrderRepository orderRepository,OrderEventProducer orderEventProducer) {
        this.orderRepository =  orderRepository;
        this.orderEventProducer = orderEventProducer;
    }
    public PageResponseDto<OrderResponseDto> getOrders(String status, int page, int size) {
        PageResponseDto<OrderResponseDto> orders = orderRepository.findAllOrders(status,page,size);
        return orders;

    }

    public OrderResponseDto create(@Valid CreateOrderDto dto){
       OrderEntity order = new OrderEntity();
        order.product=dto.product();
        order.quantity=dto.quantity();
        order.productId=dto.productId();
        order.status = "PENDING";
        orderRepository.persist(order);
        OrderCreatedEvent event = new OrderCreatedEvent(
                UUID.randomUUID(),
                "ORDER_CREATED",
                Instant.now(),
                2,
                order.id,
                order.product,
                order.productId,
                order.quantity,
                order.status
        );
        orderEventProducer.send(
                event
        );
        return new OrderResponseDto(
                order.id,
                order.product,
                order.productId,
                order.quantity,
                order.status
        );
    }

    public OrderResponseDto getOrderById(Long id){
        OrderEntity order= orderRepository.findByIdOptional(id)
                .orElseThrow(()-> new OrderNotFoundException(id));
        return new OrderResponseDto(
                order.id,
                order.product,
                order.productId,
                order.quantity,
                order.status
        );
    }

    public OrderResponseDto update(@Valid UpdateOrderDto dto, Long id){
        OrderEntity order = orderRepository.findByIdOptional(id)
                .orElseThrow(()-> new OrderNotFoundException(id));
        order.product=dto.product();
        order.quantity=dto.quantity();
        order.status = dto.status();
        order.productId=dto.productId();
        return new OrderResponseDto(
                order.id,
                order.product,
                order.productId,
                order.quantity,
                order.status
        );
    }

    @Transactional
    public void confirmOrder(Long orderId) {

        OrderEntity order = orderRepository.findByIdOptional(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        order.status = "CONFIRMED";
    }
}
