package com.evil.inc.taskman.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class BaseJpaRepository<T, ID extends Serializable> implements Repository<T, ID> {

    protected final EntityManager entityManager = Persistence.createEntityManagerFactory(
            "local-postgresql").createEntityManager();
    private final Class<T> clazz;
    private static final Logger log = LoggerFactory.getLogger(BaseJpaRepository.class);

    protected BaseJpaRepository(final Class<T> clazz) {
        this.clazz = clazz;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    protected EntityTransaction beginTransaction() {
        final EntityTransaction transaction = entityManager.getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
        }
        return transaction;
    }

    @Override
    public void save(T entity) {
        final EntityTransaction transaction = beginTransaction();
        try {
            entityManager.persist(entity);
            transaction.commit();
            log.info("Saved {}", entity.toString());
        } catch (Exception e) {
            transaction.rollback();
            log.info("Something bad happened during committing the transaction", e);
        }
    }

    @Override
    public List<T> findAll() {
        final EntityTransaction transaction = beginTransaction();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(clazz);
        Root<T> root = cq.from(clazz);
        cq.select(root);
        final List<T> result = entityManager.createQuery(cq).getResultList();
        log.info("Found {}", result.toString());
        transaction.commit();
        return result;
    }

    @Override
    public Stream<T> streamAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(clazz);
        Root<T> root = cq.from(clazz);
        cq.select(root);
        return entityManager.createQuery(cq).getResultList().stream();
    }

    @Override
    public void delete(T entity) {
        final EntityTransaction transaction = beginTransaction();
        try {
            entityManager.remove(entityManager.merge(entity));
            log.info("Removed {}", entity.toString());
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            log.info("Something bad happened during committing the transaction", e);
        }
    }

    @Override
    public void update(T entity) {
        final EntityTransaction transaction = beginTransaction();
        try {
            entityManager.merge(entity);
            log.info("Updated {}", entity.toString());
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            log.info("Something bad happened during committing the transaction", e);
        }
    }

    @Override
    public Optional<T> findById(ID id) {
        final EntityTransaction transaction = beginTransaction();
        final T entity = entityManager.find(clazz, id);
        log.info("Found {}", entity.toString());
        transaction.commit();
        return Optional.ofNullable(entity);
    }
}
