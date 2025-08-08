package com.css.gachimeokja.domain.restaurants.controller;

import com.css.gachimeokja.domain.mealitem.entity.MealItem;
import com.css.gachimeokja.domain.mealitem.repository.MealItemRepository;
import com.css.gachimeokja.domain.restaurants.entity.Restaurant;
import com.css.gachimeokja.domain.restaurants.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;
    private final MealItemRepository mealItemRepository;

    // 3. 식당 리스트 조회
    @GetMapping
    public ResponseEntity<List<Restaurant>> getRestaurants() {
        return ResponseEntity.ok(restaurantRepository.findAll());
    }

    // 4. 식당 메뉴 조회
    @GetMapping("/{id}/menu")
    public ResponseEntity<List<MealItem>> getRestaurantMenu(@PathVariable Integer id) {
        return ResponseEntity.ok(mealItemRepository.findByRestaurantId(id));
    }
}


