package com.css.gachimeokja.domain.mealgroupurchase.dto;

import lombok.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealGroupPurchaseRequestDto {
    private Integer creatorUserId;
    private Integer restaurantId;
    private String title;
    private String content;
    private Integer minOrderAmount;
    private Timestamp startAt;
    private Timestamp endAt;
    private Timestamp pickupTime;
    private String recipientName;
    private String recipientPhoneNumber;
    private String deliveryAddress;
    private String deliveryRequest;
}
