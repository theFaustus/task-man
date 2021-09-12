package com.evil.inc.taskman.command;

import com.evil.inc.taskman.utils.CommandParameterParser;
import com.evil.inc.taskman.command.exceptions.InvalidCommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandFactory {
    private static final Logger log = LoggerFactory.getLogger(CommandFactory.class);
    //    -createUser -fn='FirstName' -ln='LastName' -un='UserName'
    public static final String CREATE_USER_COMMAND = "-createUser";
    //    -showAllUsers
    public static final String SHOW_ALL_USERS_COMMAND = "-showAllUsers";
    //    -addTask -un='UserName' -tt='TaskTitle' -td='TaskDescription'
    public static final String ADD_TASK_FOR_COMMAND = "-addTask";
    //    -showTasks -un='UserName'
    public static final String SHOW_TASKS_FOR_COMMAND = "-showTasks";
    //    -deleteTask -un='UserName' -tt='TaskTitle'
    public static final String DELETE_TASK_BY_TITLE_FOR_COMMAND = "-deleteTask";

    public static final int COMMAND = 0;


    public static Command parseCommandArguments(String[] arguments) throws InvalidCommandException {
        String joinedArguments = String.join(" ", arguments); //so we can have parameter values with spaces
        String[] commandAndParameters = joinedArguments.split(" -"); //split by ' -' again so we have the command and its parameters
        final String command = arguments[COMMAND];

        return switch (command) {
            case CREATE_USER_COMMAND -> new AddUserCommand(commandAndParameters);
            case SHOW_ALL_USERS_COMMAND -> new GetAllUsersCommand();
            case ADD_TASK_FOR_COMMAND -> new AddTaskCommand(commandAndParameters);
            case DELETE_TASK_BY_TITLE_FOR_COMMAND -> new DeleteTaskCommand(commandAndParameters);
            case SHOW_TASKS_FOR_COMMAND -> new GetTasksCommand(commandAndParameters);
            default -> throw new InvalidCommandException(
                    "Oops. Unknown command [" + command + "] Please use one of the following commands: -createUser -showAllUsers -addTask");
        };
    }
}
