package ca.fun.simplyspend.service.impl;


import ca.fun.simplyspend.dao.UserDao;
import ca.fun.simplyspend.model.User;
import ca.fun.simplyspend.service.UserService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DefaultUserService implements UserService {

    @Inject
    UserDao userDao;
    @Override
    public Uni<User> getUser(int id) {
        return userDao.getUser(id);
    }

    @Override
    public Multi<User> getAllUsers(int limit) {
        return userDao.getAllUsers(limit);
    }


    @Transactional
    @Override
    public Uni<User> createUser(User user) {
        return userDao.createUser(user);
    }
}
