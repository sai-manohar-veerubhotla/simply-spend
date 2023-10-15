package ca.fun.simplyspend;

import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Produces;

@ApplicationScoped
public class DatabaseProducer {

    @Inject
    PgPool pgPool;

    @Produces
    public PgPool producePgPool() {
        return pgPool;
    }
}
