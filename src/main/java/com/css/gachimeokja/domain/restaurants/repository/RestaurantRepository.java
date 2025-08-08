package com.css.gachimeokja.domain.restaurants.repository;

import com.css.gachimeokja.domain.restaurants.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
}


