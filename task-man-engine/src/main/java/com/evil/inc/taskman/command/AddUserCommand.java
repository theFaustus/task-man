package com.evil.inc.taskman.command;

import com.evil.inc.taskman.command.exceptions.InvalidCommandException;
import com.evil.inc.taskman.entity.User;
import com.evil.inc.taskman.service.ServiceFactory;
import com.evil.inc.taskman.service.UserService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class AddUserCommand implements Command {

    private String username;
    private String firstName;
    private String lastName;

    private UserService userService = ServiceFactory.getInstance().getUserService();

    public AddUserCommand(String username, String firstName, String lastName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    @Override
    public void execute() throws InvalidCommandException {
        final User user = new User(username, firstName, lastName);
        userService.saveUser(user);
        log.info(user + "created successfully");
    }
}
