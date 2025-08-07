package com.css.gachimeokja.domain.mealgroupurchase.repository;

import com.css.gachimeokja.domain.mealgroupurchase.entity.MealGroupPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MealGroupPurchaseRepository extends JpaRepository<MealGroupPurchase, Integer> {
    
    // 상태별 조회
    List<MealGroupPurchase> findByStatus(String status);
    
    // 식당별 조회
    List<MealGroupPurchase> findByRestaurantId(Integer restaurantId);
    
    // 생성자별 조회
    List<MealGroupPurchase> findByCreatorUserId(Integer creatorUserId);
    
    // 생성일 기준 최신순 조회
    @Query("SELECT m FROM MealGroupPurchase m ORDER BY m.createdAt DESC")
    List<MealGroupPurchase> findAllOrderByCreatedAtDesc();
    
    // 종료 시간이 지나지 않은 활성 공동구매 조회
    @Query("SELECT m FROM MealGroupPurchase m WHERE m.endAt > CURRENT_TIMESTAMP AND m.status = 'OPEN' ORDER BY m.createdAt DESC")
    List<MealGroupPurchase> findActiveGroupPurchases();
    
    // ID로 조회 (삭제되지 않은 것만)
    @Query("SELECT m FROM MealGroupPurchase m WHERE m.mealGroupPurchaseId = :id")
    Optional<MealGroupPurchase> findByIdIfExists(@Param("id") Integer id);
}
