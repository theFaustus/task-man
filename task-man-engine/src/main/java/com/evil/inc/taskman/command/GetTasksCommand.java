package com.evil.inc.taskman.command;

import com.evil.inc.taskman.service.TaskService;
import com.evil.inc.taskman.service.exceptions.UserNotFoundException;
import com.evil.inc.taskman.command.exceptions.InvalidCommandException;
import com.evil.inc.taskman.service.ServiceFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GetTasksCommand implements Command {
    private String username;

    public GetTasksCommand(String username) {
        this.username = username;
    }

    private final TaskService taskService = ServiceFactory.getInstance().getTaskService();

    @Override
    public void execute() throws UserNotFoundException, InvalidCommandException {

        log.info("All tasks for [" + username + "] :");
        taskService.getTasksFor(username).forEach(t -> log.info(t.toString()));
    }


}
