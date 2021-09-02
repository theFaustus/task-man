package com.evil.inc.taskman.command;

import com.evil.inc.taskman.command.exceptions.InvalidCommandException;
import com.evil.inc.taskman.service.ServiceFactory;
import com.evil.inc.taskman.service.TaskService;
import com.evil.inc.taskman.service.exceptions.UserNotFoundException;
import com.evil.inc.taskman.utils.CommandParameterParser;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data
public class DeleteTaskCommand implements Command {
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

    @Override
    public void execute() throws UserNotFoundException, InvalidCommandException {
        taskService.deleteTaskByTitleFor(taskTitle, username);
        log.info("Task [" + taskTitle + "] deleted successfully");
    }
}
