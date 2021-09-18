package com.evil.inc.taskman.repository.impl;

import com.evil.inc.taskman.entity.User;
import com.evil.inc.taskman.repository.BaseJpaRepository;
import com.evil.inc.taskman.repository.UserRepository;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.Optional;
import java.util.stream.Stream;

public class UserJpaRepositoryImpl<T, ID extends Serializable> extends BaseJpaRepository<User, Long> implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(UserJpaRepositoryImpl.class);
    public static UserJpaRepositoryImpl<User, Long> INSTANCE;

    public static UserJpaRepositoryImpl<User, Long> getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserJpaRepositoryImpl<>();
        }

        return INSTANCE;
    }

    private UserJpaRepositoryImpl() {
        super(User.class);
    }

    @Override
    public Optional<User> findByUsername(final String username) {
        final EntityTransaction t = beginTransaction();
        final TypedQuery<User> query = getEntityManager().createQuery(
                "select u from User u where u.userName = :username", User.class);
        query.setParameter("username", username);
        final Optional<User> user = Optional.of(query.getSingleResult());
        t.commit();
        return user;
    }


    @Override
    public Stream<User> streamAll() {
        final EntityTransaction t = beginTransaction();
        final TypedQuery<User> query = getEntityManager().createQuery(
                "select u from User u", User.class);
        final Stream<User> queryResultStream = query.getResultStream();
        t.commit();
        return queryResultStream;
    }

    @Override
    public void deleteById(final Long id) {
        final EntityTransaction t = beginTransaction();
        final Optional<User> byId = findById(id);
        byId.ifPresent(this::delete);
        t.commit();
    }

    @Override
    public Session getSession() {
        return getEntityManager().unwrap(Session.class);
    }
}
