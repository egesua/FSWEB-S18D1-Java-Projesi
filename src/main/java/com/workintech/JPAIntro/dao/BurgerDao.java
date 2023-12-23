package com.workintech.JPAIntro.dao;

import com.workintech.JPAIntro.entity.BreadType;
import com.workintech.JPAIntro.entity.Burger;

import java.util.List;

public interface BurgerDao {
    Burger save(Burger burger);
    List<Burger> findAll();

    Burger findById(long id);

    Burger update(Burger burger);

    Burger remove(long id);

    List<Burger> findByPrice(Integer price);

    List<Burger> findByBreadType(BreadType breadType);

    List<Burger> findByContent(String content);
}
