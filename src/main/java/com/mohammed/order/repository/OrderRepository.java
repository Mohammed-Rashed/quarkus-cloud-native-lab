package com.mohammed.order.repository;

import com.mohammed.order.dto.OrderResponseDto;
import com.mohammed.order.entity.OrderEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<OrderEntity> {
    public List<OrderResponseDto> getOrders(String status,int page, int size) {
        return findAllPaginated(status,page,size).stream()
                .map(order -> new OrderResponseDto(
                        order.id,
                        order.product,
                        order.quantity,
                        order.status
                )).toList();
    }

    public List<OrderEntity> findAllPaginated(String status,int page, int size) {
        if (status == null) {
            return findAll(Sort.by("createdAt").descending())
                    .page(page, size)
                    .list();
        }

        return find(
                "status",
                Sort.by("createdAt").descending(),
                status
        )
                .page(page, size)
                .list();
    }
}
