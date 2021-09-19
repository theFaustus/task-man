package com.evil.inc.taskman.command;

import com.evil.inc.taskman.command.exceptions.InvalidCommandException;
import com.evil.inc.taskman.entity.User;
import com.evil.inc.taskman.service.ServiceFactory;
import com.evil.inc.taskman.service.UserService;
import com.evil.inc.taskman.utils.CommandParameterParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AddUserCommand implements Command, Runnable {

    private static final Logger log = LoggerFactory.getLogger(AddUserCommand.class);

    private String username;
    private String firstName;
    private String lastName;

    private UserService userService = ServiceFactory.getInstance().getUserService();

    public AddUserCommand(String[] commandAndParameters) throws InvalidCommandException {
        if (commandAndParameters.length < 4) {
            throw new InvalidCommandException(
                    "Oops. Please refer to the usage of the command : " + "-createUser -fn='FirstName' -ln='LastName' -un='UserName'");
        }

        this.username = CommandParameterParser.getUsername(commandAndParameters);
        this.firstName = CommandParameterParser.getFirstName(commandAndParameters);
        this.lastName = CommandParameterParser.getLastName(commandAndParameters);
    }

    public AddUserCommand(final String username, final String firstName, final String lastName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public void execute() throws InvalidCommandException {
        final User user = new User(username, firstName, lastName);
        userService.create(user);
        log.info(user + "created successfully");
    }

    @Override
    public void run() {
        log.info("{}", Thread.currentThread().getName());
        try {
            execute();
        } catch (InvalidCommandException e) {
            System.out.println("Oops! Something went wrong during creation of the user : " + e.getMessage());
            log.trace(e.getMessage(), e);
        }
    }
}
