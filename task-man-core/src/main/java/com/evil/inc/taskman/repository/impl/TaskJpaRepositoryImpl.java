package com.evil.inc.taskman.repository.impl;

import com.evil.inc.taskman.entity.Task;
import com.evil.inc.taskman.entity.User;
import com.evil.inc.taskman.repository.BaseJpaRepository;
import com.evil.inc.taskman.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Slf4j
public class TaskJpaRepositoryImpl<T, ID extends Serializable> extends BaseJpaRepository<Task, Long> implements TaskRepository {

    public static TaskJpaRepositoryImpl<Task, Long> INSTANCE;

    private TaskJpaRepositoryImpl() {
        super(Task.class);
    }

    public static TaskJpaRepositoryImpl<Task, Long> getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TaskJpaRepositoryImpl<>();
        }

        return INSTANCE;
    }

    @Override
    public Session getSession() {
        return getEntityManager().unwrap(Session.class);
    }

    @Override
    public void deleteTaskByTitleFor(final String taskTitle, final String username) {
        final EntityTransaction transaction = beginTransaction();
        try {
            final Query query = getEntityManager().createQuery(
                    "delete from Task t where t.title = :title and t.id in (select t.id from Task t join t.users u where u.userName = :username)");
            query.setParameter("username", username);
            query.setParameter("title", taskTitle);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            log.info("Something bad happened during committing the transaction", e);
        }
    }

    @Override
    public List<Task> getTasksFor(final String username) {
        final TypedQuery<Task> query = getEntityManager().createQuery(
                "select t from Task t join t.users u where u.userName = :username", Task.class);
        query.setParameter("username", username);
        return query.getResultList();
    }

    @Override
    public int saveTaskFor(final Task task, final String username) {
        return 0;
    }
}
