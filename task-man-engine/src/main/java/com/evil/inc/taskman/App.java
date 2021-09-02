package com.evil.inc.taskman;

//import com.evil.inc.taskman.service.ServiceFactory;
import com.evil.inc.taskman.entity.Task;
import com.evil.inc.taskman.entity.User;
import com.evil.inc.taskman.service.ServiceFactory;
        import com.evil.inc.taskman.service.TaskService;
import com.evil.inc.taskman.service.UserService;
import com.evil.inc.taskman.command.exceptions.InvalidCommandException;
import com.evil.inc.taskman.service.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class App {
    public static void main(String[] args) throws UserNotFoundException, InvalidCommandException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        TaskService taskService = ServiceFactory.getInstance().getTaskService();

        userService.saveUser(new User("sponge-bob-333", "Sponge", "Bob"));
        final User byUsername = userService.getByUsername("sponge-bob-333");
        System.out.println(byUsername);
        final Task task = new Task("clean bikini bottom", "we need a clean city");
        byUsername.addTask(task);
        userService.saveUser(byUsername);
        final User byUsername2 = userService.getByUsername("sponge-bob-333");
        System.out.println(byUsername2);
        byUsername2.removeTask(task);
        userService.saveUser(byUsername);
        final User byId = userService.getById(26L);
        System.out.println(byId);


//        Command command = CommandFactory.parseCommandArguments(args);
//        try {
//             command.execute();
//        } catch (InvalidCommandException | UserNotFoundException e) {
//            log.error("Something bad happened during entering command",e.getMessage());
//            System.exit(0);
//        }

    }
}
