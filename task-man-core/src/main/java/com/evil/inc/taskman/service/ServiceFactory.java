package com.evil.inc.taskman.service;


import com.evil.inc.taskman.aop.proxy.ActionEmailConfirmationHandler;
import com.evil.inc.taskman.repository.RepositoryFactory;
import com.evil.inc.taskman.service.impl.EmailServiceImpl;
import com.evil.inc.taskman.service.impl.TaskServiceImpl;
import com.evil.inc.taskman.service.impl.UserServiceImpl;

import java.lang.reflect.Proxy;

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
        final UserServiceImpl instance = UserServiceImpl.getInstance(
                RepositoryFactory.getInstance().getUserRepository());
        ActionEmailConfirmationHandler emailConfirmationHandler = new ActionEmailConfirmationHandler(instance);
        final UserService userService = (UserService) Proxy.newProxyInstance(UserService.class.getClassLoader(),
                                                                             new Class[]{UserService.class},
                                                                             emailConfirmationHandler);
        return userService;
    }

    public EmailService getEmailService() {
        return EmailServiceImpl.getInstance();
    }

    public TaskService getTaskService() {
        return TaskServiceImpl.getInstance(RepositoryFactory.getInstance().getTaskRepository());
    }
}
