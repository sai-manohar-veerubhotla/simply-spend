package ca.fun.simplyspend.resource;

import ca.fun.simplyspend.model.Order;
import ca.fun.simplyspend.service.OrderService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.jboss.resteasy.reactive.RestPath;

@Path("orders")
public class OrderResource {

    @Inject
    OrderService orderService;

    @Path("{id}")
    @GET
    @Produces("application/json")
    public Uni<Order> getOrderById(int id) {
        return orderService.getOrderById(id);
    }


    @Path("{id}/{status}")
    @GET
    @Produces("application/json")
    public Uni<Order> getOrderByIdAndStatus(@RestPath int id, @RestPath Order.Status status) {
        return orderService.getOrderByIdAndStatus(id, status);
    }

    @Path("all/{limit}")
    @GET
    @Produces("application/json")
    public Multi<Order> getAllOrders(@RestPath int limit) {
        return orderService.getAllOrders(limit);
    }

}
