package ca.fun.simplyspend.service;

import ca.fun.simplyspend.model.User;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface UserService {

    Uni<User> getUser(int id);

    Multi<User> getAllUsers(int limit);
}
