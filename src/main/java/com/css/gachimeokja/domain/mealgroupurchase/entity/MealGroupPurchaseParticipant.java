package com.css.gachimeokja.domain.mealgroupurchase.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "meal_group_purchase_participants")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MealGroupPurchaseParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participation_id")
    private Integer participationId;

    @Column(name = "meal_group_purchase_id", nullable = false)
    private Integer mealGroupPurchaseId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "total_order_amount", nullable = false)
    private Integer totalOrderAmount;

    @Column(name = "participated_at", nullable = false)
    private Timestamp participatedAt;

    @Column(name = "status", nullable = false)
    private String status;

    @Builder
    public MealGroupPurchaseParticipant(Integer mealGroupPurchaseId, Integer userId,
                                       Integer totalOrderAmount) {
        this.mealGroupPurchaseId = mealGroupPurchaseId;
        this.userId = userId;
        this.totalOrderAmount = totalOrderAmount;
        this.participatedAt = new Timestamp(System.currentTimeMillis());
        this.status = "PENDING";
    }
}