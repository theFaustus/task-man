package com.evil.inc.taskman.repository;


import java.util.List;

import com.evil.inc.taskman.entity.Task;

public interface TaskRepository {

    /**
     * Deletes tasks, by title, for the specified username
     *
     * @param taskTitle - a <code>String</code> representing the title of the task which will be deleted
     * @param username  - a <code>String</code> representing the username given in order to get the tasks for
     */
    void deleteTaskByTitleFor(String taskTitle, String username);

    /**
     * Finds tasks for the specified username
     *
     * @param username - a <code>String</code> representing the username given in order to get the tasks for
     */
    List<Task> getTasksFor(String username);

    /**
     * Adds tasks to a specific user, by giving his username
     *
     * @param task -a <code>Task</code> representing the task which will be added
     * @param username -a <code>String</code> representing the username of the user, for whom the task will be added
     */
    int saveTaskFor(Task task, String username);

}
