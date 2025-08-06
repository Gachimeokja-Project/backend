package com.css.gachimeokja.domain.mealitem.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "meal_items")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MealItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_item_id")
    private Integer mealItemId;

    @Column(name = "restaurant_id", nullable = false)
    private Integer restaurantId;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column
    private String description;

    @Column(name = "unit_price", nullable = false)
    private Integer unitPrice;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @Builder
    public MealItem(Integer restaurantId, String itemName, String description,
                   Integer unitPrice, String imageUrl) {
        this.restaurantId = restaurantId;
        this.itemName = itemName;
        this.description = description;
        this.unitPrice = unitPrice;
        this.imageUrl = imageUrl;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}