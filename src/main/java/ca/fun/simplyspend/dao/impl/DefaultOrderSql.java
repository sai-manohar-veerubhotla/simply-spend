package ca.fun.simplyspend.dao.impl;

import ca.fun.simplyspend.dao.OrderDao;
import ca.fun.simplyspend.model.Order;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.reactive.mutiny.Mutiny;

@ApplicationScoped
public class DefaultOrderSql implements OrderDao {

    @Inject
    Mutiny.SessionFactory sessionFactory;

    @Override
    public Uni<Order> getOrderById(int id) {
        return sessionFactory.withSession(session -> session.find(Order.class, id));
    }

    @Override
    public Uni<Order> getOrderByIdAndStatus(int id, Order.Status status) {
        String query = """
                select o from orders o where o.id = :id and o.status = :status
                """;
        return sessionFactory.withSession(session -> session.createQuery(query, Order.class)
                .setParameter("id", id)
                .setParameter("status", status)
                .getResultList()
                .flatMap(orders -> {
                    if (orders.isEmpty()) {
                        return Uni.createFrom().failure(new RuntimeException("Order not found"));
                    } else if (orders.size() > 1) {
                        return Uni.createFrom().failure(new RuntimeException("More than one order found"));
                    }
                    return Uni.createFrom().item(orders.get(0));
                })

        );
    }

    @Override
    public Multi<Order> getAllOrders(int limit) {
        return sessionFactory.withSession(session -> session.createQuery("select o from orders o", Order.class)
                        .setMaxResults(limit)
                        .getResultList())
                .onItem()
                .transformToMulti(orders -> Multi.createFrom().iterable(orders));
    }
}
