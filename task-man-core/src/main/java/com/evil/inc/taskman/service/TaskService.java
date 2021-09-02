package com.evil.inc.taskman.service;

import java.util.List;

import com.evil.inc.taskman.entity.Task;
import com.evil.inc.taskman.service.exceptions.UserNotFoundException;

public interface TaskService {

    /**
     * Finds tasks for the specified username
     *
     * @param username a <code>String</code> the username given in order to get the tasks for
     */
    List<Task> getAllFor(String username) throws UserNotFoundException;


    /**
     * Deletes tasks, by title, for the specified username
     *
     * @param taskTitle a <code>String</code> representing the title of the task which will be deleted
     * @param username  a <code>String</code> representing the username given in order to get the tasks for
     * @throws UserNotFoundException when the user is not found, this exception is thrown
     */
    //one more logical feature
    void deleteByTitleAndUsername(String taskTitle, String username) throws UserNotFoundException;


    /**
     * Adds tasks to a specific user, by giving his username
     *
     * @param taskTitle       a <code>String</code> representing the title of the task which will be added
     * @param taskDescription a <code>String</code> representing the description of the task which will be added
     * @param username        a <code>String</code> representing the username of the user, for whom the task will be added
     * @throws UserNotFoundException when the user is not found, this exception is thrown
     */
    int createFor(String taskTitle, String taskDescription, String username) throws UserNotFoundException;


}
