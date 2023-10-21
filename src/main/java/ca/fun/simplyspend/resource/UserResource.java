package ca.fun.simplyspend.resource;

import ca.fun.simplyspend.data.CreateUserRequest;
import ca.fun.simplyspend.data.CreateUserValidationResult;
import ca.fun.simplyspend.data.SimpleSpendCoreResponse;
import ca.fun.simplyspend.exception.InvalidUserEmailException;
import ca.fun.simplyspend.exception.InvalidUserPasswordException;
import ca.fun.simplyspend.exception.InvalidUsernameException;
import ca.fun.simplyspend.exception.SimplySpendCoreException;
import ca.fun.simplyspend.model.User;
import ca.fun.simplyspend.service.UserService;
import ca.fun.simplyspend.validator.UserValidator;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.jboss.resteasy.reactive.RestPath;

import java.util.List;
import java.util.Optional;

@Path("users")
public class UserResource {

    @Inject
    UserService userService;

    @Inject
    UserValidator userValidator;

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


    @Path("create")
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Uni<SimpleSpendCoreResponse<User, List<String>>> createUser(User user) {
        CreateUserRequest createUserRequest = new CreateUserRequest(
                user.getFirstName(),
                user.getLastName(),
                user.getEmailId(),
                user.getAge(),
                user.getPhone(),
                user.getRole(),
                user.getPassword(),
                user.getManagerName(),
                user.getAddress(),
                user.getTag()
        );

        return userValidator.validate(createUserRequest)
                .flatMap(validationResults -> {
                    if (validationResults.isValid()) {
                        return userService.createUser(user)
                                .map(user1 ->
                                        new SimpleSpendCoreResponse<>(200,
                                                Optional.of(user1),
                                                Optional.empty()
                                        )
                                );

                    }
                    List<SimplySpendCoreException> exceptions = validationResults.validationResults()
                            .stream()
                            .filter(validationResult -> !validationResult.isValid())
                            .map(CreateUserValidationResult.ValidationResult::userField)
                            .map(userField -> switch (userField) {
                                        case USER_NAME ->
                                                new InvalidUsernameException(403, "Invalid username. Username already exists");
                                        case EMAIL_ID -> new InvalidUserEmailException(400, "Invalid email");
                                        case PASSWORD ->
                                                new InvalidUserPasswordException(400, "Invalid password. Password must be at least 8 characters long" +
                                                        " and must contain at least one uppercase letter, one lowercase letter, one number and one special character");
                                        default -> throw new IllegalStateException("Unexpected value: " + userField);
                                    }
                            )
                            .toList();

                    return Uni.createFrom()
                            .item(new SimpleSpendCoreResponse<>(
                                    exceptions.get(0).getErrorCode(),
                                    Optional.<User>empty(),
                                    Optional.of(exceptions.stream()
                                            .map(SimplySpendCoreException::getMessage)
                                            .toList())
                            ));
                });
    }

}
