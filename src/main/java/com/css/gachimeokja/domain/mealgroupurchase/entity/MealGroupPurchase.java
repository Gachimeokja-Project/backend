package com.css.gachimeokja.domain.mealgroupurchase.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "meal_group_purchases")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MealGroupPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_group_purchase_id")
    private Integer mealGroupPurchaseId;

    @Column(name = "creator_user_id", nullable = false)
    private Integer creatorUserId;

    @Column(name = "restaurant_id", nullable = false)
    private Integer restaurantId;

    @Column(nullable = false)
    private String title;

    @Column
    private String content;

    @Column(name = "min_order_amount")
    private Integer minOrderAmount;

    @Column(name = "current_amount", nullable = false)
    private Integer currentAmount;

    @Column(name = "current_members", nullable = false)
    private Integer currentMembers;

    @Column(name = "start_at", nullable = false)
    private Timestamp startAt;

    @Column(name = "end_at", nullable = false)
    private Timestamp endAt;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @Column(name = "pickup_time")
    private Timestamp pickupTime;

    @Column(name = "recipient_name", nullable = false)
    private String recipientName;

    @Column(name = "recipient_phone_number", nullable = false)
    private String recipientPhoneNumber;

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @Column(name = "delivery_request")
    private String deliveryRequest;

    @Builder
    public MealGroupPurchase(Integer creatorUserId, Integer restaurantId, String title,
                            String content, Integer minOrderAmount, Timestamp startAt,
                            Timestamp endAt, Timestamp pickupTime, String recipientName,
                            String recipientPhoneNumber, String deliveryAddress,
                            String deliveryRequest) {
        this.creatorUserId = creatorUserId;
        this.restaurantId = restaurantId;
        this.title = title;
        this.content = content;
        this.minOrderAmount = minOrderAmount;
        this.currentAmount = 0;
        this.currentMembers = 0;
        this.startAt = startAt;
        this.endAt = endAt;
        this.status = "OPEN";
        this.pickupTime = pickupTime;
        this.recipientName = recipientName;
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.deliveryAddress = deliveryAddress;
        this.deliveryRequest = deliveryRequest;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}