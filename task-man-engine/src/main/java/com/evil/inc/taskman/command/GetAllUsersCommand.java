package com.evil.inc.taskman.command;

import com.evil.inc.taskman.service.UserService;
import com.evil.inc.taskman.service.ServiceFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data
public class GetAllUsersCommand implements Command {

    private UserService userService = ServiceFactory.getInstance().getUserService();


    @Override
    public void execute() {
        log.info("All users : ");
        userService.getAll().forEach(u -> log.info(u.toString()));
    }
}
