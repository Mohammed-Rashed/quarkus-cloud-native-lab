package com.mohammed.order.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String product;

    @Column(nullable = false)
    public int quantity;

    @Column(nullable = false)
    public String status;
}
