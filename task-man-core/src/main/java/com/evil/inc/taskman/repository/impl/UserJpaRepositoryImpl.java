package com.evil.inc.taskman.repository.impl;

import com.evil.inc.taskman.entity.User;
import com.evil.inc.taskman.repository.BaseJpaRepository;
import com.evil.inc.taskman.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.Optional;

@Slf4j
public class UserJpaRepositoryImpl<T, ID extends Serializable> extends BaseJpaRepository<User, Long> implements UserRepository {

    public static UserJpaRepositoryImpl<User, Long> INSTANCE;

    public static UserJpaRepositoryImpl<User, Long> getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserJpaRepositoryImpl<>();
        }

        return INSTANCE;
    }

    private UserJpaRepositoryImpl() {
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
    public void deleteById(final Long id) {
        final Optional<User> byId = findById(id);
        byId.ifPresent(this::delete);
    }

    @Override
    public Session getSession() {
        return getEntityManager().unwrap(Session.class);
    }
}
