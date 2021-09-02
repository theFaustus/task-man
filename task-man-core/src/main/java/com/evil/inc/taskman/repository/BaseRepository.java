package com.evil.inc.taskman.repository;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.Serializable;
import java.util.Optional;

@Slf4j
public abstract class BaseRepository<T, ID extends Serializable> implements Repository<T, ID> {
    private final StandardServiceRegistry registry;
    private final Metadata metadata;
    private SessionFactory sessionFactory;

    private Class<T> clazz;

    public BaseRepository() {
        registry = new StandardServiceRegistryBuilder().configure("/hibernate.cfg.xml").build();
        metadata = new MetadataSources(registry).getMetadataBuilder().build();
        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }

    @Override
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(final T entity) {
        final Session session = getSession();
        Transaction t = session.beginTransaction();
        session.saveOrUpdate(entity);
        t.commit();
    }

    @Override
    public Optional<T> findById(ID id) {
        final Session session = getSession();
        Transaction t = session.beginTransaction();
        final T entity = session.get(clazz, id);
        t.commit();
        return Optional.ofNullable(entity);
    }

    @Override
    public void delete(final T entity) {
        final Session session = getSession();
        Transaction t = session.beginTransaction();
        session.delete(entity);
        t.commit();
    }

    @Override
    public void update(final T entity) {
        final Session session = getSession();
        Transaction t = session.beginTransaction();
        session.update(entity);
        t.commit();
    }
}
