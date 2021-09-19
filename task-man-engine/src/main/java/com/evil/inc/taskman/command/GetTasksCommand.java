package com.evil.inc.taskman.command;

import com.evil.inc.taskman.service.TaskService;
import com.evil.inc.taskman.service.exceptions.UserNotFoundException;
import com.evil.inc.taskman.command.exceptions.InvalidCommandException;
import com.evil.inc.taskman.service.ServiceFactory;
import com.evil.inc.taskman.utils.CommandParameterParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetTasksCommand implements Command, Runnable {
    private static final Logger log = LoggerFactory.getLogger(GetTasksCommand.class);
    private final TaskService taskService = ServiceFactory.getInstance().getTaskService();
    private final String username;

    public GetTasksCommand(String[] commandAndParameters) throws InvalidCommandException {
        if (commandAndParameters.length < 2) {
            throw new InvalidCommandException(
                    "Oops. Please refer to the usage of the command : -showTasks -un='UserName'");
        }
        this.username = CommandParameterParser.getUsername(commandAndParameters);
    }

    public GetTasksCommand(final String username) {
        this.username = username;
    }

    @Override
    public void execute() throws UserNotFoundException, InvalidCommandException {
        log.info("All tasks for [" + username + "] :");
        taskService.getAllFor(username).forEach(t -> log.info(t.toString()));
    }


    @Override
    public void run() {
        log.info("{}", Thread.currentThread().getName());
        try {
            execute();
        } catch (InvalidCommandException e) {
            System.out.println("Oops! Something went wrong during getting of the tasks : " + e.getMessage());
            log.trace(e.getMessage(), e);
        }
    }
}
