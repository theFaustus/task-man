package com.evil.inc.taskman;

//import com.evil.inc.taskman.service.ServiceFactory;
import com.evil.inc.taskman.entity.Task;
import com.evil.inc.taskman.entity.User;
import com.evil.inc.taskman.service.ServiceFactory;
import com.evil.inc.taskman.service.TaskService;
import com.evil.inc.taskman.service.UserService;
import com.evil.inc.taskman.command.exceptions.InvalidCommandException;
import com.evil.inc.taskman.service.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws UserNotFoundException, InvalidCommandException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        TaskService taskService = ServiceFactory.getInstance().getTaskService();

        userService.create(new User("sponge-bob-333", "Sponge", "Bob"));
        final User byUsername = userService.getByUsername("sponge-bob-333");
        log.info(byUsername.toString());
        final Task task = new Task("clean bikini bottom 1", "we need a clean city");
        byUsername.addTask(task);
        userService.create(byUsername);
        final User byUsername2 = userService.getByUsername("sponge-bob-333");
        log.info(byUsername2.toString());
//        byUsername2.removeTask(task);
        userService.create(byUsername);
        final User byId = userService.getById(26L);
        log.info(byId.toString());

        log.info(taskService.getAllFor("sponge-bob-333").toString());
        taskService.deleteByTitleAndUsername("clean bikini bottom 1", "sponge-bob-333");
        log.info(taskService.getAllFor("sponge-bob-333").toString());

        userService.createAndAssign(new User("bdylan", "bob", "dylan"),
                                    new Task("write a song", "should be about love"));

//        Command command = CommandFactory.parseCommandArguments(args);
//        try {
//             command.execute();
//        } catch (InvalidCommandException | UserNotFoundException e) {
//            log.error("Something bad happened during entering command",e.getMessage());
//            System.exit(0);
//        }

    }
}
