package com.css.gachimeokja.domain.mealgroupurchase.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealGroupPurchaseDeleteResponseDto {
    private String message;
    private Integer deletedId;
    private String deletedTitle;
}
