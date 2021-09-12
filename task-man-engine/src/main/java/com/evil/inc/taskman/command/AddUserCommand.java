package com.evil.inc.taskman.command;

import com.evil.inc.taskman.command.exceptions.InvalidCommandException;
import com.evil.inc.taskman.entity.User;
import com.evil.inc.taskman.service.ServiceFactory;
import com.evil.inc.taskman.service.UserService;
import com.evil.inc.taskman.utils.CommandParameterParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AddUserCommand implements Command {

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


    @Override
    public void execute() throws InvalidCommandException {
        final User user = new User(username, firstName, lastName);
        userService.create(user);
        log.info(user + "created successfully");
    }
}
