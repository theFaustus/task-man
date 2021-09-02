package com.evil.inc.taskman.command;

import com.evil.inc.taskman.utils.CommandParameterParser;
import com.evil.inc.taskman.command.exceptions.InvalidCommandException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommandFactory {

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
//        String joinedArguments = String.join(" ", arguments); //so we can have parameter values with spaces
//        String[] commandAndParameters = joinedArguments.split(" -"); //split by ' -' again so we have the command and its parameters
//        final String command = arguments[COMMAND];

        switch (arguments[0]) {
            case CREATE_USER_COMMAND:
                if (arguments.length < 4) {
                    throw new InvalidCommandException("Oops. Please refer to the usage of the command : " + "-createUser -fn='FirstName' -ln='LastName' -un='UserName'");
                }
                return new AddUserCommand(CommandParameterParser.getUsername(arguments),
                                          CommandParameterParser.getFirstName(arguments),
                                          CommandParameterParser.getLastName(arguments));

            case SHOW_ALL_USERS_COMMAND:
                return new GetAllUsersCommand();

            case ADD_TASK_FOR_COMMAND:
                if (arguments.length < 4) {
                    throw new InvalidCommandException(
                            "Oops. Please refer to the usage of the command : -addTask -un='UserName' -tt='TaskTitle' -td='TaskDescription'");
                }
                return new AddTaskCommand(CommandParameterParser.getTaskTitle(arguments),
                                          CommandParameterParser.getTaskDescription(arguments),
                                          CommandParameterParser.getUsername(arguments));

            case DELETE_TASK_BY_TITLE_FOR_COMMAND:
                if (arguments.length < 3) {
                    throw new InvalidCommandException(
                            "Oops. Please refer to the usage of the command: -deleteTask -un='UserName' -tt='TaskTitle'");
                }
                return new DeleteTaskCommand(CommandParameterParser.getTaskTitle(arguments), CommandParameterParser.getUsername(arguments));

            case SHOW_TASKS_FOR_COMMAND:
                if (arguments.length < 2) {
                    throw new InvalidCommandException(
                            "Oops. Please refer to the usage of the command : -showTasks -un='UserName'");
                }
                return new GetTasksCommand(CommandParameterParser.getUsername(arguments));
            default:
                throw new InvalidCommandException("Oops. Unknown command [" + arguments[0] + "] Please use one of the following commands: -createUser -showAllUsers -addTask");
        }
    }
}
