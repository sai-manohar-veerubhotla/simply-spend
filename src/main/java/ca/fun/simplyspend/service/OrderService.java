package ca.fun.simplyspend.service;

import ca.fun.simplyspend.model.Order;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface OrderService {
    Uni<Order> getOrderById(int id);

    Uni<Order> getOrderByIdAndStatus(int id, Order.Status status);

    Multi<Order> getAllOrders(int limit);
}
