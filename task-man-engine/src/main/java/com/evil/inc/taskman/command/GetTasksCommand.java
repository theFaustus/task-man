package com.evil.inc.taskman.command;

import com.evil.inc.taskman.service.TaskService;
import com.evil.inc.taskman.service.exceptions.UserNotFoundException;
import com.evil.inc.taskman.command.exceptions.InvalidCommandException;
import com.evil.inc.taskman.service.ServiceFactory;
import com.evil.inc.taskman.utils.CommandParameterParser;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class GetTasksCommand implements Command {
    private String username;

    public GetTasksCommand(String[] commandAndParameters) throws InvalidCommandException {
        if (commandAndParameters.length < 2) {
            throw new InvalidCommandException(
                    "Oops. Please refer to the usage of the command : -showTasks -un='UserName'");
        }
        this.username = CommandParameterParser.getUsername(commandAndParameters);
    }

    private final TaskService taskService = ServiceFactory.getInstance().getTaskService();

    @Override
    public void execute() throws UserNotFoundException, InvalidCommandException {

        log.info("All tasks for [" + username + "] :");
        taskService.getAllFor(username).forEach(t -> log.info(t.toString()));
    }


}
