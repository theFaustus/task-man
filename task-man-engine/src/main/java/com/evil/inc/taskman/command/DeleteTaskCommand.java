package com.evil.inc.taskman.command;

import com.evil.inc.taskman.command.exceptions.InvalidCommandException;
import com.evil.inc.taskman.service.ServiceFactory;
import com.evil.inc.taskman.service.TaskService;
import com.evil.inc.taskman.service.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class DeleteTaskCommand implements Command {
    private String taskTitle;
    private String username;

    public DeleteTaskCommand(String taskTitle, String username) {
        this.taskTitle = taskTitle;
        this.username = username;
    }


    private final TaskService taskService = ServiceFactory.getInstance().getTaskService();


    @Override
    public void execute() throws UserNotFoundException, InvalidCommandException {
        taskService.deleteTaskByTitleFor(taskTitle, username);
        log.info("Task [" + taskTitle + "] deleted successfully");
    }
}
