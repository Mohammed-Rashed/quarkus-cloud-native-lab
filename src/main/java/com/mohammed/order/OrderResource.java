package com.mohammed.order;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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
}
