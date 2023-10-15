package ca.fun.simplyspend.dao.impl;

import ca.fun.simplyspend.dao.UserDao;
import ca.fun.simplyspend.model.User;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.function.Function;

@ApplicationScoped
public class DefaultUserSql implements UserDao {

    @Inject
    PgPool pgClient;


    @Override
    public Uni<User> getUser(int id) {
        String query = String.format("""
                 SELECT * FROM "user" WHERE "id" = %d
                """, id);
        System.out.println("Query: " + query);
        return pgClient.query(query)
                .execute()
                .onItem().transformToUni(rows -> Uni.createFrom().item(rows.iterator().next()))
                .onItem().transform(map());
    }

    private static Function<Row, User> map() {
        return row -> new User(
                row.getInteger("id"),
                row.getString("firstname"),
                row.getString("lastname"),
                row.getString("emailid"),
                row.getInteger("age"),
                row.getString("phone"),
                User.Role.valueOf(row.getString("role")),
                row.getString("password"),
                row.getString("managername"),
                row.getString("address"),
                row.getString("tag")
        );
    }

    @Override
    public Multi<User> getAllUsers(int limit) {
        return pgClient.query(String.format("""
                           SELECT * FROM "user" LIMIT %d
                        """, limit))
                .execute()
                .onItem().transformToMulti(rows -> Multi.createFrom().iterable(rows))
                .onItem().transform(map());

    }


    @Inject
    @ConfigProperty(name = "myapp.schema.create", defaultValue = "true")
    boolean schemaCreate;

    void config(@Observes StartupEvent ev) {
        if (schemaCreate) {
            initDb();
        }
    }

    private void initDb() {
        pgClient.query("DROP TABLE IF EXISTS \"user\"").execute()
                .flatMap(r -> pgClient.query("""
                        CREATE TABLE "user" (id SERIAL PRIMARY KEY,
                         firstName TEXT NOT NULL,
                         lastName TEXT NOT NULL,
                         emailId TEXT NOT NULL UNIQUE,
                         age INT2 NOT NULL,
                         phone TEXT NOT NULL UNIQUE,
                         role TEXT NOT NULL,
                         password TEXT NOT NULL,
                         managerName TEXT NOT NULL,
                         address TEXT NOT NULL,
                         tag TEXT NOT NULL
                         )
                        """).execute())
                .flatMap(r -> pgClient.query("""
                                INSERT INTO "user" (firstName, lastName, emailId, age, phone, role, password, managerName, address, tag) 
                                VALUES ('Sai', 'Veeru', 'manohar@example.com', 27, '1234567890', 'REPORTEE','password', 'Dave K', 'Markham', 'Seniør Software Enginner' )
                                """)
                        .execute())
                .flatMap(r -> pgClient.query("""
                                INSERT INTO "user" (firstName, lastName, emailId, age, phone, role, password, managerName, address, tag) 
                                VALUES ('Manasa', 'Kalluri', 'manasa@example.com', 26, '1234567891', 'REPORTEE','password', 'Manohar', 'BM', 'Seniør Data Enginner' )
                                """)
                        .execute())
                .await()
                .indefinitely();
    }

}
