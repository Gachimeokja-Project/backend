package com.css.gachimeokja.domain.mealgroupurchase.controller;

import com.css.gachimeokja.domain.mealgroupurchase.dto.MealGroupPurchaseRequestDto;
import com.css.gachimeokja.domain.mealgroupurchase.dto.MealGroupPurchaseResponseDto;
import com.css.gachimeokja.domain.mealgroupurchase.dto.MealGroupPurchaseDeleteResponseDto;
import com.css.gachimeokja.domain.mealgroupurchase.service.MealGroupPurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/meal-group-purchases")
@RequiredArgsConstructor
public class MealGroupPurchaseController {

    private final MealGroupPurchaseService mealGroupPurchaseService;

    /**
     * 1. 식사공동구매 리스트 조회 API
     * GET /meal-group-purchases
     */
    @GetMapping
    public ResponseEntity<List<MealGroupPurchaseResponseDto>> getAllMealGroupPurchases(
            @RequestParam(value = "active", required = false, defaultValue = "false") boolean activeOnly) {
        try {
            List<MealGroupPurchaseResponseDto> purchases;
            if (activeOnly) {
                purchases = mealGroupPurchaseService.getActiveMealGroupPurchases();
            } else {
                purchases = mealGroupPurchaseService.getAllMealGroupPurchases();
            }
            return ResponseEntity.ok(purchases);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 2. 식사공동구매 등록 API
     * POST /meal-group-purchases
     * Header: X-User-Id (필수)
     */
    @PostMapping
    public ResponseEntity<MealGroupPurchaseResponseDto> createMealGroupPurchase(
            @RequestHeader("X-User-Id") Integer userId,
            @RequestBody MealGroupPurchaseRequestDto requestDto) {
        try {
            // 요청 DTO에 사용자 ID 설정
            requestDto.setCreatorUserId(userId);
            MealGroupPurchaseResponseDto response = mealGroupPurchaseService.createMealGroupPurchase(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 3. 식사공동구매 수정 API
     * PATCH /meal-group-purchases/{id}
     * Header: X-User-Id (필수)
     */
    @PatchMapping("/{id}")
    public ResponseEntity<MealGroupPurchaseResponseDto> updateMealGroupPurchase(
            @PathVariable Integer id,
            @RequestHeader("X-User-Id") Integer userId,
            @RequestBody MealGroupPurchaseRequestDto requestDto) {
        try {
            // 요청 DTO에 사용자 ID 설정
            requestDto.setCreatorUserId(userId);
            MealGroupPurchaseResponseDto response = mealGroupPurchaseService.updateMealGroupPurchase(id, requestDto);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("찾을 수 없습니다")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else if (e.getMessage().contains("권한이 없습니다")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 4. 식사공동구매 삭제 API
     * DELETE /meal-group-purchases/{id}
     * Header: X-User-Id (필수)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MealGroupPurchaseDeleteResponseDto> deleteMealGroupPurchase(
            @PathVariable Integer id,
            @RequestHeader("X-User-Id") Integer userId) {
        try {
            MealGroupPurchaseDeleteResponseDto response = mealGroupPurchaseService.deleteMealGroupPurchase(id, userId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("찾을 수 없습니다")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else if (e.getMessage().contains("권한이 없습니다")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            } else if (e.getMessage().contains("참여자가 있는")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 5. 식사공동구매 상세조회 API
     * GET /meal-group-purchases/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<MealGroupPurchaseResponseDto> getMealGroupPurchaseById(@PathVariable Integer id) {
        try {
            MealGroupPurchaseResponseDto response = mealGroupPurchaseService.getMealGroupPurchaseById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("찾을 수 없습니다")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 식당별 식사공동구매 조회 API (추가 기능)
     * GET /meal-group-purchases/restaurant/{restaurantId}
     */
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<MealGroupPurchaseResponseDto>> getMealGroupPurchasesByRestaurant(
            @PathVariable Integer restaurantId) {
        try {
            // 이 기능을 위해서는 Service에 메서드를 추가해야 합니다
            // 현재는 기본 기능만 구현했습니다
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 사용자별 생성한 식사공동구매 조회 API (추가 기능)
     * GET /meal-group-purchases/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MealGroupPurchaseResponseDto>> getMealGroupPurchasesByUser(
            @PathVariable Integer userId) {
        try {
            // 이 기능을 위해서는 Service에 메서드를 추가해야 합니다
            // 현재는 기본 기능만 구현했습니다
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
