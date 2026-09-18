package com.mohammed.order;

import com.mohammed.order.dto.CreateOrderDto;
import com.mohammed.order.dto.OrderResponseDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/orders")
public class OrderResource {

    private final OrderService orderService;
    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<OrderResponseDto> getOrders(@QueryParam("status") String status) {
        return orderService.getOrders(status);
    }

    @Path("/status")
    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrderStatus() {
        return "ORDERS WORK FINE";
    }

    @Path("/{id}")
    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderById(@PathParam("id") Long id) {
        OrderResponseDto order= orderService.getOrderById(id);
        return Response.ok(order).build();

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public OrderResponseDto createOrder(CreateOrderDto dto) {
        return orderService.create(dto);

    }
}
