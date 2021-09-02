package com.evil.inc.taskman.service;


import com.evil.inc.taskman.repository.RepositoryFactory;
import com.evil.inc.taskman.service.impl.TaskServiceImpl;
import com.evil.inc.taskman.service.impl.UserServiceImpl;

public class ServiceFactory {
    private static ServiceFactory INSTANCE;

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServiceFactory();
        }

        return INSTANCE;
    }

    public UserService getUserService() {
        return UserServiceImpl.getInstance(RepositoryFactory.getInstance().getUserRepository());
    }

    public TaskService getTaskService() {
        return TaskServiceImpl.getInstance(RepositoryFactory.getInstance().getTaskRepository());
    }
}
