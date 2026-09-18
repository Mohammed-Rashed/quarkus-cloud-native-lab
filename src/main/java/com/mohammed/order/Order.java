package com.mohammed.order;

public class Order {
    public Long id;
    public String product;
    public int quantity;
    public Order(Long id, String product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }
}
