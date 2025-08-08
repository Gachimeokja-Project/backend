package com.css.gachimeokja.domain.mealitem.repository;

import com.css.gachimeokja.domain.mealitem.entity.MealItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealItemRepository extends JpaRepository<MealItem, Integer> {
    List<MealItem> findByRestaurantId(Integer restaurantId);
}


