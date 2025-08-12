package com.css.gachimeokja.domain.mealgroupurchase.dto;

import com.css.gachimeokja.domain.mealgroupurchase.entity.MealGroupPurchase;
import lombok.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealGroupPurchaseResponseDto {
    private Integer mealGroupPurchaseId;
    private Integer creatorUserId;
    private Integer restaurantId;
    private String title;
    private String content;
    private Integer minOrderAmount;
    private Integer currentAmount;
    private Integer currentMembers;
    private Timestamp startAt;
    private Timestamp endAt;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp pickupTime;
    private String recipientName;
    private String recipientPhoneNumber;
    private String deliveryAddress;
    private String deliveryRequest;

    // Entity -> DTO 변환 메서드
    public static MealGroupPurchaseResponseDto from(MealGroupPurchase entity) {
        return MealGroupPurchaseResponseDto.builder()
                .mealGroupPurchaseId(entity.getMealGroupPurchaseId())
                .creatorUserId(entity.getCreatorUserId())
                .restaurantId(entity.getRestaurantId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .minOrderAmount(entity.getMinOrderAmount())
                .currentAmount(entity.getCurrentAmount())
                .currentMembers(entity.getCurrentMembers())
                .startAt(entity.getStartAt())
                .endAt(entity.getEndAt())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .pickupTime(entity.getPickupTime())
                .recipientName(entity.getRecipientName())
                .recipientPhoneNumber(entity.getRecipientPhoneNumber())
                .deliveryAddress(entity.getDeliveryAddress())
                .deliveryRequest(entity.getDeliveryRequest())
                .build();
    }
}
