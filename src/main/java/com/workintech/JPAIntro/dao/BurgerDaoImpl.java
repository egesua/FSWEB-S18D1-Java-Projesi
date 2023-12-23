package com.workintech.JPAIntro.dao;

import com.workintech.JPAIntro.entity.BreadType;
import com.workintech.JPAIntro.entity.Burger;
import com.workintech.JPAIntro.exceptions.BurgerException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public class BurgerDaoImpl implements BurgerDao{
    private final EntityManager entityManager;
    @Autowired
    public BurgerDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Transactional
    @Override
    public Burger save(Burger burger) {
        entityManager.persist(burger);
        return burger;
    }

    @Override
    public List<Burger> findAll() {
        TypedQuery<Burger> foundAll = entityManager.createQuery("SELECT b from Burger b", Burger.class);
        return foundAll.getResultList();
    }

    @Override
    public Burger findById(long id) {
        Burger burger = entityManager.find(Burger.class, id);
        if(burger == null) {
            throw new BurgerException("Burger with given id is not exist: " + id, HttpStatus.NOT_FOUND);
        }
        return burger;
    }
    @Transactional
    @Override
    public Burger update(Burger burger) {
        return entityManager.merge(burger);
    }
    @Transactional
    @Override
    public Burger remove(long id) {
        Burger foundBurger = findById(id);
        entityManager.remove(foundBurger);
        return foundBurger;
    }

    @Override
    public List<Burger> findByPrice(Integer price) {
        TypedQuery<Burger> foundList = entityManager.createQuery("SELECT b FROM Burger WHERE b.price > :price ORDER BY b.price DESC", Burger.class);
        foundList.setParameter("price", price);
        return foundList.getResultList();
    }

    @Override
    public List<Burger> findByBreadType(BreadType breadType) {
        TypedQuery<Burger> breadTypeQuery = entityManager.createQuery("SELECT b FROM Burger WHERE b.breadType = :breadType ORDER BY b.name DESC", Burger.class);
        breadTypeQuery.setParameter("breadType", breadType);
        return breadTypeQuery.getResultList();
    }

    @Override
    public List<Burger> findByContent(String content) {
        TypedQuery<Burger> contentQuery = entityManager.createQuery("SELECT b FROM Burger WHERE b.contents LIKE CONCAT('%', :content, '%') ORDER BY b.name",Burger.class);
        contentQuery.setParameter("content", content);
        return contentQuery.getResultList();
    }
}
