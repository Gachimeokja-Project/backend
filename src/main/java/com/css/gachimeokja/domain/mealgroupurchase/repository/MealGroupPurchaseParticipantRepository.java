package com.css.gachimeokja.domain.mealgroupurchase.repository;

import com.css.gachimeokja.domain.mealgroupurchase.entity.MealGroupPurchaseParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MealGroupPurchaseParticipantRepository extends JpaRepository<MealGroupPurchaseParticipant, Integer> {
    List<MealGroupPurchaseParticipant> findByMealGroupPurchaseId(Integer mealGroupPurchaseId);
    Optional<MealGroupPurchaseParticipant> findByMealGroupPurchaseIdAndUserId(Integer mealGroupPurchaseId, Integer userId);
}


