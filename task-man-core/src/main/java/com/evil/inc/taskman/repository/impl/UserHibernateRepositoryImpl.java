package com.evil.inc.taskman.repository.impl;

import com.evil.inc.taskman.repository.BaseHibernateRepository;
import com.evil.inc.taskman.entity.User;
import com.evil.inc.taskman.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class UserHibernateRepositoryImpl<T, ID extends Serializable> extends BaseHibernateRepository<User, Long> implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(UserHibernateRepositoryImpl.class);
    public static UserHibernateRepositoryImpl<User, Long> INSTANCE;

    public static UserHibernateRepositoryImpl<User, Long> getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserHibernateRepositoryImpl<>();
        }

        return INSTANCE;
    }

    private UserHibernateRepositoryImpl() {
        super(User.class);
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
    public Stream<User> streamAll() {
        final Session session = getSession();
        final Transaction t = session.beginTransaction();
        final Query<User> query = session.createQuery("select u from User u", User.class);
        final Stream<User> userStream = query.stream();
        t.commit();
        return userStream;
    }

    @Override
    public void deleteById(final Long id) {
        final Optional<User> byId = findById(id);
        byId.ifPresent(this::delete);
    }
}
