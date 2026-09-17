package com.mohammed.order;

import com.mohammed.order.dto.CreateOrderDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/orders")
public class OrderResource {

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
    public Order createOrder(CreateOrderDto dto) {
        return new Order(
                1L,
                dto.product,
                dto.quantity
        );
    }
}
