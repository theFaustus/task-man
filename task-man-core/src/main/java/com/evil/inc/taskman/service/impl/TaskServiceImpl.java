package com.evil.inc.taskman.service.impl;

import java.util.List;

import com.evil.inc.taskman.entity.Task;
import com.evil.inc.taskman.repository.TaskRepository;
import com.evil.inc.taskman.service.TaskService;
import com.evil.inc.taskman.service.exceptions.UserNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskServiceImpl implements TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);
    public static TaskServiceImpl INSTANCE;

    private final TaskRepository taskRepository;

    private TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public static TaskServiceImpl getInstance(TaskRepository taskRepository) {
        if (INSTANCE == null) {
            INSTANCE = new TaskServiceImpl(taskRepository);
        }

        return INSTANCE;
    }

    @Override
    public List<Task> getAllFor(String username) {
        log.info("Entered getTasksFor with username = {}", username);
        return taskRepository.getTasksFor(username);
    }

    //one more logical feature
    @Override
    public void deleteByTitleAndUsername(String taskTitle, String username) {
        log.info("Entered deleteTaskByTitleFor with taskTitle = {} and username = {}", taskTitle, username);
        taskRepository.deleteTaskByTitleFor(taskTitle, username);
    }

    @Override
    public int createFor(String taskTitle, String taskDescription, String username) throws UserNotFoundException {
        log.info("Entered addTaskFor with taskTitle = {} , taskDescription = {} and username = {}", taskTitle,
                 taskDescription, username);
        return taskRepository.saveTaskFor(new Task(taskTitle, taskDescription), username);
    }


}
