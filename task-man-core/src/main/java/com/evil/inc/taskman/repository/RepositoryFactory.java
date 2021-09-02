package com.evil.inc.taskman.repository;

import com.evil.inc.taskman.repository.impl.UserHibernateRepositoryImpl;
import com.evil.inc.taskman.repository.impl.TaskJDBCRepositoryImpl;
import com.evil.inc.taskman.repository.impl.UserJpaRepositoryImpl;

public class RepositoryFactory {
    private static RepositoryFactory INSTANCE;

    private RepositoryFactory() {
    }

    public static RepositoryFactory getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new RepositoryFactory();
        }

        return INSTANCE;
    }

    public UserRepository getUserRepository(){
//        return UserFileRepositoryImpl.getInstance();
        return UserJpaRepositoryImpl.getInstance();
    }

    public TaskRepository getTaskRepository(){
//        return UserFileRepositoryImpl.getInstance();
        return TaskJDBCRepositoryImpl.getInstance();
    }
}
