package com.css.gachimeokja.domain.restaurants.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "restaurants")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Integer restaurantId;

    @Column(name = "restaurant_name", nullable = false)
    private String restaurantName;

    @Column(name = "restaurant_address", nullable = false)
    private String restaurantAddress;

    @Column(name = "restaurant_phone", nullable = false)
    private String restaurantPhone;

    @Column(name = "business_hours")
    private String businessHours;

    @Column(name = "delivery_fee")
    private Integer deliveryFee;

    @Column(name = "min_order_amount")
    private Integer minOrderAmount;

    @Column(name = "payment_methods")
    private String paymentMethods;

    @Column(name = "restaurant_description")
    private String restaurantDescription;

    @Column(name = "restaurant_category", nullable = false)
    private String restaurantCategory;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "review_count")
    private Integer reviewCount;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @Builder
    public Restaurant(String restaurantName, String restaurantAddress, String restaurantPhone,
                     String businessHours, Integer deliveryFee, Integer minOrderAmount,
                     String paymentMethods, String restaurantDescription,
                     String restaurantCategory) {
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.restaurantPhone = restaurantPhone;
        this.businessHours = businessHours;
        this.deliveryFee = deliveryFee;
        this.minOrderAmount = minOrderAmount;
        this.paymentMethods = paymentMethods;
        this.restaurantDescription = restaurantDescription;
        this.restaurantCategory = restaurantCategory;
        this.rating = 0.0f;
        this.reviewCount = 0;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}