package com.evil.inc.taskman.command;

import com.evil.inc.taskman.command.exceptions.InvalidCommandException;
import com.evil.inc.taskman.service.exceptions.UserNotFoundException;

public interface Command {
    public void execute() throws UserNotFoundException, InvalidCommandException;
}
