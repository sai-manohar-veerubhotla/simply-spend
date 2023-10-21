package ca.fun.simplyspend.data;

import ca.fun.simplyspend.model.User;

/**
 * Request object for creating a user
 * @param firstName  first name of the user
 * @param lastName last name of the user
 * @param emailId email id of the user
 * @param age age of the user
 * @param phone phone number of the user
 * @param role role of the user
 * @param password password of the user
 * @param managerName manager name of the user
 * @param address address of the user
 * @param tag tag of the user
 */
public record CreateUserRequest(String firstName,
                                String lastName,
                                String emailId,
                                int age,
                                String phone,
                                User.Role role,
                                String password,
                                String managerName,
                                String address,
                                String tag) {

}
