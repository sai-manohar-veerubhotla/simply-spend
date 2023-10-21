package ca.fun.simplyspend.dao.impl;

import ca.fun.simplyspend.dao.UserDao;
import ca.fun.simplyspend.model.User;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.FlushMode;
import org.hibernate.reactive.mutiny.Mutiny;

@ApplicationScoped
public class DefaultUserSql implements UserDao {

    @Inject
    Mutiny.SessionFactory sessionFactory;


    @Override
    public Uni<User> getUser(int id) {
        String query = String.format("""
                select u from users u where u.id = %d
                """, id);
        Log.info("Query: " + query);
        return sessionFactory.withSession(session -> session
                .createQuery(query, User.class)
                .setFlushMode(FlushMode.ALWAYS)
                .getSingleResult()
        );
    }


    @Override
    public Multi<User> getAllUsers(int limit) {
        String query = """
                select u from users u
                """;
        Log.info("Query: " + query);
        return sessionFactory.withSession(session -> session.createQuery(query, User.class)
                        .setMaxResults(limit)
                        .setFlushMode(FlushMode.ALWAYS)
                        .getResultList())
                .onItem()
                .transformToMulti(users -> Multi.createFrom().iterable(users));
    }

    @Override
    public Uni<User> createUser(User user) {
        return sessionFactory.withSession(session -> session.persist(user)
                .chain(session::flush)
                .onItem()
                .transform(ignore -> user)
        );
    }


}
