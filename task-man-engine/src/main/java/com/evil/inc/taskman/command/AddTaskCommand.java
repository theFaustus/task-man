package com.evil.inc.taskman.command;

import com.evil.inc.taskman.command.exceptions.InvalidCommandException;
import com.evil.inc.taskman.service.ServiceFactory;
import com.evil.inc.taskman.service.TaskService;
import com.evil.inc.taskman.service.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddTaskCommand implements Command {

    private String taskTitle;
    private String taskDescription;
    private String username;

    public AddTaskCommand(String taskTitle, String taskDescription, String username) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.username = username;
    }


    private final TaskService taskService = ServiceFactory.getInstance().getTaskService();


    @Override
    public void execute() throws UserNotFoundException, InvalidCommandException {

        taskService.addTaskFor(taskTitle, taskDescription, username);
        log.info("Task [" + taskTitle + "] created successfully");
    }
}

