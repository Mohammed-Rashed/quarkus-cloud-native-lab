package com.mohammed.order.repository;

import com.mohammed.order.dto.OrderResponseDto;
import com.mohammed.order.entity.OrderEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<OrderEntity> {
    public List<OrderResponseDto> findByStatus(String status) {
        return find("status",status).list().stream()
                .map(order -> new OrderResponseDto(
                        order.id,
                        order.product,
                        order.quantity,
                        order.status
                )).toList();
    }
}
