package com.evil.inc.taskman.utils;

import com.evil.inc.taskman.command.exceptions.InvalidCommandException;

import java.util.Arrays;

public class CommandParameterParser {
    public static String getUsername(String[] commandParameters) throws InvalidCommandException {
        return getParameterValue(commandParameters, "-un");
    }

    public static String getFirstName(String[] commandParameters) throws InvalidCommandException {
        return getParameterValue(commandParameters, "-fn");
    }

    public static String getLastName(String[] commandParameters) throws InvalidCommandException {
        return getParameterValue(commandParameters, "-ln");
    }

    public static String getTaskDescription(String[] commandParameters) throws InvalidCommandException {
        return getParameterValue(commandParameters, "-td");
    }

    public static String getTaskTitle(String[] commandParameters) throws InvalidCommandException {
        return getParameterValue(commandParameters, "-tt");
    }

    public static String getParameterValue(String[] commandParameters, String commandName) throws InvalidCommandException {
        return Arrays.stream(commandParameters)
                .filter(parameter ->
                        parameter.startsWith(commandName + "=")
                                && !parameter.substring(parameter.indexOf("=") + 1).isEmpty())
                .map(p -> p.substring(p.indexOf("=") + 1))
                .findFirst()
                .orElseThrow(() -> new InvalidCommandException(
                        "Oops. Parameter [-" + commandName + "] not found in " +
                                Arrays.toString(commandParameters)));
    }
}
