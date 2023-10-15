package ca.fun.simplyspend.dao;

import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Query;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class SeedData {

    @Inject
    PgPool pgClient;

    @Inject
    @ConfigProperty(name = "myapp.schema.create", defaultValue = "true")
    boolean schemaCreate;

    void config(@Observes StartupEvent ev) {
        if (schemaCreate) {
            initDb();
        }
    }

    // for now, we just drop the tables and recreate it on startup
    private void initDb() {
        Log.info("Dropping tables and recreating them");
        pgClient.query("""
                       
                        DROP TABLE IF EXISTS items CASCADE;
                        DROP TABLE IF EXISTS orders CASCADE;
                        DROP TABLE IF EXISTS users CASCADE;
                                              
                         """).execute()
                .flatMap(r -> {
                    Log.info("Creating tables");
                    Query<RowSet<Row>> query = pgClient.query("""
                             CREATE TABLE users (
                                                   id SERIAL PRIMARY KEY,
                                                   first_name TEXT NOT NULL,
                                                   last_name TEXT NOT NULL,
                                                   email_id TEXT NOT NULL UNIQUE,
                                                   age INT NOT NULL,
                                                   phone TEXT NOT NULL,
                                                   role TEXT NOT NULL CHECK (role IN ('MGR', 'REPORTEE', 'ADMIN')),
                                                   password TEXT NOT NULL,
                                                   manager_name TEXT NOT NULL,
                                                   address TEXT NOT NULL,
                                                   tag TEXT NOT NULL
                            );
                            CREATE TABLE orders (
                                                    id SERIAL PRIMARY KEY,
                                                    status TEXT NOT NULL CHECK (status IN ('REQUESTED', 'APPROVED', 'DENIED')),
                                                    reason TEXT NOT NULL,
                                                    comment TEXT NOT NULL,
                                                    user_id INT NOT NULL,
                                                    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
                            );
                                                            
                            CREATE TABLE items (
                                                   id SERIAL PRIMARY KEY,
                                                   type TEXT NOT NULL CHECK (type IN ('HARDWARE', 'SOFTWARE', 'STATIONARY', 'TRAINING', 'MISC')),
                                                   name TEXT NOT NULL,
                                                   price INT NOT NULL,
                                                   description TEXT NOT NULL,
                                                   order_id INT NOT NULL,
                                                   FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
                            );
                                                    
                                                                    
                             """);
                    return query
                            .execute();
                })
                .flatMap(r -> {
                            Log.info("Tables created");
                            Log.info("Inserting test data");
                            Query<RowSet<Row>> inserts = pgClient.query("""
                                    INSERT INTO users (first_name, last_name, email_id, age, phone, role, password, manager_name, address, tag)
                                    VALUES
                                        ('John', 'Doe', 'john.doe@example.com', 30, '1234567890', 'MGR', 'password123', 'James Smith', '123 Elm St', 'Senior Manager'),
                                        ('Jane', 'Smith', 'jane.smith@example.com', 25, '9876543210', 'REPORTEE', 'jane123', 'John Doe', '456 Maple St', 'Junior Developer');
                                    INSERT INTO orders (status, reason, comment, user_id)
                                    VALUES
                                        ('REQUESTED', 'Need new hardware', 'Requesting a new laptop', 1),
                                        ('APPROVED', 'Software license', 'License for development tool approved', 2);
                                    INSERT INTO items (type, name, price, description, order_id)
                                    VALUES
                                        ('HARDWARE', 'Laptop', 1000, 'Dell XPS 15', 1),
                                        ('SOFTWARE', 'Development Tool License', 200, 'License for IntelliJ IDEA', 2),
                                        ('STATIONARY', 'Notebook', 10, 'A4 sized notebook', 1);
                                    """);
                            return inserts
                                    .execute();
                        }
                )
                .await()
                .indefinitely();
    }
}
