package com.evil.inc.taskman.repository;


import java.util.List;
import java.util.Optional;

import com.evil.inc.taskman.entity.User;

public interface UserRepository {

    /**
     * Saves a user in database
     *
     * @param user -(User) the user with firstName, lastName and username
     */
    void saveUser(User user);

    /**
     * Returns a user, by his username
     *
     * @param username -(String) the username given in order to find the user
     */
    Optional<User> findByUsername(String username);

    /**
     * Gets all the users from database
     */
    List<User> findAllUsers();

    void update(User user);

    /**
     * Deletes an user by his id
     *
     * @param id -(Long) the user's id
     * @return
     */
    void deleteUserById(Long id);
}
