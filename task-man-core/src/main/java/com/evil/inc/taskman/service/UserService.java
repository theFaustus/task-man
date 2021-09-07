package com.evil.inc.taskman.service;

import java.util.List;

import com.evil.inc.taskman.annotations.ActionEmailConfirmation;
import com.evil.inc.taskman.entity.User;
import com.evil.inc.taskman.service.exceptions.UserNotFoundException;

public interface UserService {

    /**
     * Saves a user in database
     *
     * @param user - a <code>User</code>  representing the user with firstName, lastName and username
     * @return the saved user
     */
//    @ActionEmailConfirmation(email = {"jhoonnyc@gmail.com"})
    void create(User user);

    /**
     * Returns a user, by his username
     *
     * @param username  - a <code>String</code> representing the username given in order to find the user
     * @return the user, with his id, firstName, lastName and userName
     */
    User getByUsername(String username);


    /**
     * Deletes an user by his id
     *
     * @param id - a <code>Long</code> representing the user's id
     * @return int the operation is executed
     */
    //one more logical feature
    void deleteById(Long id) throws UserNotFoundException;


    /**
     * Gets all the users from database
     *
     * @return all the users from database
     */
//    @ActionEmailConfirmation(email = {"jhoonnyc@gmail.com"})
    List<User> getAll();

    void update(User user);

    //    @ActionEmailConfirmation(email = {"jhoonnyc@gmail.com"})
    User getById(Long id);
}
