package com.css.gachimeokja.domain.mealgroupurchase.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "meal_group_purchase_items")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MealGroupPurchaseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_group_menu_id")
    private Integer mealGroupMenuId;

    @Column(name = "meal_items_id", nullable = false)
    private Integer mealItemsId;

    @Column(name = "meal_group_purchase_id", nullable = false)
    private Integer mealGroupPurchaseId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "participated_at", nullable = false)
    private Timestamp participatedAt;

    @Builder
    public MealGroupPurchaseItem(Integer mealItemsId, Integer mealGroupPurchaseId,
                                Integer userId) {
        this.mealItemsId = mealItemsId;
        this.mealGroupPurchaseId = mealGroupPurchaseId;
        this.userId = userId;
        this.participatedAt = new Timestamp(System.currentTimeMillis());
    }
}