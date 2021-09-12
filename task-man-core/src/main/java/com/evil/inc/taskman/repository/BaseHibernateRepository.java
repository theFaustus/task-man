package com.evil.inc.taskman.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Optional;

public abstract class BaseHibernateRepository<T, ID extends Serializable> implements Repository<T, ID> {
    private final StandardServiceRegistry registry;
    private final Metadata metadata;
    private SessionFactory sessionFactory;
    private static final Logger log = LoggerFactory.getLogger(BaseHibernateRepository.class);

    private final Class<T> clazz;

    protected BaseHibernateRepository(final Class<T> clazz) {
        this.clazz = clazz;
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
