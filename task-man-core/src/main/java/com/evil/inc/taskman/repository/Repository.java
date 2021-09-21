package com.evil.inc.taskman.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface Repository<T, ID extends Serializable> {
    Session getSession();

    void save(T object);

    Optional<T> findById(ID id);

    List<T> findAll();

    Stream<T> streamAll();

    void delete(T entity);

    void update(T entity);
}
