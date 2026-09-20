package com.mohammed.order.repository;

import com.mohammed.order.dto.OrderResponseDto;
import com.mohammed.order.dto.PageResponseDto;
import com.mohammed.order.entity.OrderEntity;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
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

    public PanacheQuery<OrderEntity> findOrders(String status) {

        Sort sort = Sort.by("createdAt").descending();

        if (status == null) {
            return findAll(sort);
        }

        return find("status", sort, status);
    }
    public PageResponseDto<OrderResponseDto> findAllOrders(String status,int page, int size) {
        PanacheQuery<OrderEntity> query =
                findOrders(status);
        long total = query.count();
        List<OrderResponseDto> data = query
                .page(page, size)
                .list()
                .stream()
                .map(order -> new OrderResponseDto(
                        order.id,
                        order.product,
                        order.quantity,
                        order.status
                ))
                .toList();
        int totalPages = (int) Math.ceil((double) total / size);
        return new PageResponseDto<>(
                data,
                page,
                size,
                total,
                totalPages
        );
    }
}
