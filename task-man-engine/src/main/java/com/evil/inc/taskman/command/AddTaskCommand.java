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
public class AddTaskCommand implements Command {

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


    private final TaskService taskService = ServiceFactory.getInstance().getTaskService();


    @Override
    public void execute() throws UserNotFoundException, InvalidCommandException {

        taskService.addTaskFor(taskTitle, taskDescription, username);
        log.info("Task [" + taskTitle + "] created successfully");
    }
}

