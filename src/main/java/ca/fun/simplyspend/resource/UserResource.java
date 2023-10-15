package ca.fun.simplyspend.resource;

import ca.fun.simplyspend.model.User;
import ca.fun.simplyspend.service.UserService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.jboss.resteasy.reactive.RestPath;

@Path("users")
public class UserResource {

    @Inject
    UserService userService;

    @Path("{id}")
    @GET
    @Produces("application/json")
    public Uni<User> getUserById(@RestPath int id) {
        return userService.getUser(id);
    }


    @Path("all/{limit}")
    @GET
    @Produces("application/json")
    public Multi<User> getAllUsers(@RestPath int limit) {
        return userService.getAllUsers(limit);
    }

}
