package com.evil.inc.taskman.repository.impl;

import com.evil.inc.taskman.repository.BaseHibernateRepository;
import com.evil.inc.taskman.entity.User;
import com.evil.inc.taskman.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Slf4j
public class UserHibernateRepositoryImpl<T, ID extends Serializable> extends BaseHibernateRepository<User, Long> implements UserRepository {

    public static UserHibernateRepositoryImpl<User, Long> INSTANCE;

    public static UserHibernateRepositoryImpl<User, Long> getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserHibernateRepositoryImpl<>();
        }

        return INSTANCE;
    }

    private UserHibernateRepositoryImpl() {
    }

    @Override
    public Optional<User> findByUsername(final String username) {
        final Session session = getSession();
        final Transaction t = session.beginTransaction();
        final Query<User> query = session.createQuery("select u from User u where u.userName = :username", User.class);
        query.setParameter("username", username);
        final Optional<User> user = Optional.of(query.uniqueResult());
        t.commit();
        return user;
    }

    @Override
    public List<User> findAll() {
        final Session session = getSession();
        final Transaction t = session.beginTransaction();
        final Query<User> query = session.createQuery("select u from User u", User.class);
        final List<User> list = query.list();
        t.commit();
        return list;
    }

    @Override
    public void deleteById(final Long id) {
        final Optional<User> byId = findById(id);
        byId.ifPresent(this::delete);
    }
}
