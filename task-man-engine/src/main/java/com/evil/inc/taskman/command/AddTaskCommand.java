package com.evil.inc.taskman.command;

import com.evil.inc.taskman.command.exceptions.InvalidCommandException;
import com.evil.inc.taskman.service.ServiceFactory;
import com.evil.inc.taskman.service.TaskService;
import com.evil.inc.taskman.service.exceptions.UserNotFoundException;
import com.evil.inc.taskman.utils.CommandParameterParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AddTaskCommand implements Command, Runnable {
    private static final Logger log = LoggerFactory.getLogger(AddTaskCommand.class);

    private String taskTitle;
    private String taskDescription;
    private String username;

    public AddTaskCommand(String[] commandAndParameters) throws InvalidCommandException {
        if (commandAndParameters.length < 4) {
            throw new InvalidCommandException(
                    "Oops. Please refer to the usage of the command : -addTask -un='UserName' -tt='TaskTitle' -td='TaskDescription'");
        }
        this.taskTitle = CommandParameterParser.getTaskTitle(commandAndParameters);
        this.taskDescription = CommandParameterParser.getTaskDescription(commandAndParameters);
        this.username = CommandParameterParser.getUsername(commandAndParameters);
    }

    public AddTaskCommand(final String taskTitle, final String taskDescription, final String username) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.username = username;
    }

    private final TaskService taskService = ServiceFactory.getInstance().getTaskService();


    @Override
    public void execute() throws UserNotFoundException, InvalidCommandException {

        taskService.createFor(taskTitle, taskDescription, username);
        log.info("Task [" + taskTitle + "] created successfully");
    }

    @Override
    public void run() {
        log.info("{}", Thread.currentThread().getName());
        try {
            execute();
        } catch (InvalidCommandException e) {
            System.out.println("Oops! Something went wrong during creation of the task : " + e.getMessage());
            log.trace(e.getMessage(), e);
        }
    }
}

