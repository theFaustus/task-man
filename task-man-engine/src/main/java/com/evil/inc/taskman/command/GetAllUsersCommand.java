package com.evil.inc.taskman.command;

import com.evil.inc.taskman.service.UserService;
import com.evil.inc.taskman.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GetAllUsersCommand implements Command {
    private static final Logger log = LoggerFactory.getLogger(GetAllUsersCommand.class);

    private UserService userService = ServiceFactory.getInstance().getUserService();


    @Override
    public void execute() {
        log.info("All users : ");
        userService.getAll().forEach(u -> log.info(u.toString()));
    }
}
