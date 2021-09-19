package com.evil.inc.taskman.command;

import com.evil.inc.taskman.command.exceptions.InvalidCommandException;
import com.evil.inc.taskman.service.ServiceFactory;
import com.evil.inc.taskman.service.TaskService;
import com.evil.inc.taskman.service.exceptions.UserNotFoundException;
import com.evil.inc.taskman.utils.CommandParameterParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DeleteTaskCommand implements Command, Runnable {
    private static final Logger log = LoggerFactory.getLogger(DeleteTaskCommand.class);

    private String taskTitle;
    private String username;

    private final TaskService taskService = ServiceFactory.getInstance().getTaskService();

    public DeleteTaskCommand(final String[] commandAndParameters) throws InvalidCommandException {
        if (commandAndParameters.length < 3) {
            throw new InvalidCommandException(
                    "Oops. Please refer to the usage of the command: -deleteTask -un='UserName' -tt='TaskTitle'");
        }
        this.taskTitle = CommandParameterParser.getTaskTitle(commandAndParameters);
        this.username = CommandParameterParser.getUsername(commandAndParameters);
    }

    public DeleteTaskCommand(final String taskTitle, final String username) {
        this.taskTitle = taskTitle;
        this.username = username;
    }

    @Override
    public void execute() throws UserNotFoundException, InvalidCommandException {
        taskService.deleteByTitleAndUsername(taskTitle, username);
        log.info("Task [" + taskTitle + "] deleted successfully");
    }

    @Override
    public void run() {
        log.info("{}", Thread.currentThread().getName());
        try {
            execute();
        } catch (InvalidCommandException e) {
            System.out.println("Oops! Something went wrong during delete of the task : " + e.getMessage());
            log.trace(e.getMessage(), e);
        }
    }
}
