package com.evil.inc.taskman.repository;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class BaseJpaRepository<T, ID extends Serializable> implements Repository<T, ID> {

    protected final EntityManager entityManager = Persistence.createEntityManagerFactory(
            "local-postgresql").createEntityManager();
    private final Class<T> clazz;

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
        } catch (Exception e) {
            transaction.rollback();
            log.info("Something bad happened during fetching an entity");
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
        transaction.commit();
        return result;
    }

    @Override
    public void delete(T entity) {
        final EntityTransaction transaction = beginTransaction();
        try {
            entityManager.remove(entityManager.merge(entity));
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            log.info("Something bad happened during fetching an entity");
        }
    }

    @Override
    public void update(T entity) {
        final EntityTransaction transaction = beginTransaction();
        try {
            entityManager.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            log.info("Something bad happened during fetching an entity");
        }
    }

    @Override
    public Optional<T> findById(ID id) {
        final EntityTransaction transaction = beginTransaction();
        final T entity = entityManager.find(clazz, id);
        transaction.commit();
        return Optional.ofNullable(entity);
    }
}
