package ca.fun.simplyspend.service.impl;

import ca.fun.simplyspend.dao.OrderDao;
import ca.fun.simplyspend.model.Order;
import ca.fun.simplyspend.service.OrderService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DefaultOrderService implements OrderService {

    @Inject
    OrderDao orderDao;


    @Override
    public Uni<Order> getOrderById(int id) {
        return orderDao.getOrderById(id);
    }

    @Override
    public Uni<Order> getOrderByIdAndStatus(int id, Order.Status status) {
        return orderDao.getOrderByIdAndStatus(id, status);
    }

    @Override
    public Multi<Order> getAllOrders(int limit) {
        return orderDao.getAllOrders(limit);
    }
}
