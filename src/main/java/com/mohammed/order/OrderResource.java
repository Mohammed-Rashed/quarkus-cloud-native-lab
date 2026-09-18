package com.mohammed.order;

import com.mohammed.order.dto.CreateOrderDto;
import com.mohammed.order.dto.OrderResponseDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/orders")
public class OrderResource {

    private final OrderService orderService;
    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrders() {
        return "Orders API";
    }

    @Path("/status")
    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrderStatus() {
        return "Order service is running";
    }

    @Path("/{id}")
    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public Order getOrderById(@PathParam("id") Long id) {
        Order order=new Order(id,"Product A",10);
        return order;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public OrderResponseDto createOrder(CreateOrderDto dto) {
        return orderService.create(dto);

    }
}
