package ca.fun.simplyspend.dao;


import ca.fun.simplyspend.model.User;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface UserDao {
    Uni<User> getUser(int id);

    Multi<User> getAllUsers(int limit);
}
