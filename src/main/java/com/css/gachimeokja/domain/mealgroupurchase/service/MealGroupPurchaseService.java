package com.css.gachimeokja.domain.mealgroupurchase.service;

import com.css.gachimeokja.domain.mealgroupurchase.dto.MealGroupPurchaseRequestDto;
import com.css.gachimeokja.domain.mealgroupurchase.dto.MealGroupPurchaseResponseDto;
import com.css.gachimeokja.domain.mealgroupurchase.dto.MealGroupPurchaseDeleteResponseDto;
import com.css.gachimeokja.domain.mealgroupurchase.entity.MealGroupPurchase;
import com.css.gachimeokja.domain.mealgroupurchase.repository.MealGroupPurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MealGroupPurchaseService {

    private final MealGroupPurchaseRepository mealGroupPurchaseRepository;

    /**
     * 식사공동구매 리스트 조회
     */
    public List<MealGroupPurchaseResponseDto> getAllMealGroupPurchases() {
        List<MealGroupPurchase> purchases = mealGroupPurchaseRepository.findAllOrderByCreatedAtDesc();
        return purchases.stream()
                .map(MealGroupPurchaseResponseDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 활성 식사공동구매 리스트 조회
     */
    public List<MealGroupPurchaseResponseDto> getActiveMealGroupPurchases() {
        List<MealGroupPurchase> purchases = mealGroupPurchaseRepository.findActiveGroupPurchases();
        return purchases.stream()
                .map(MealGroupPurchaseResponseDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 식사공동구매 상세 조회
     */
    public MealGroupPurchaseResponseDto getMealGroupPurchaseById(Integer id) {
        MealGroupPurchase purchase = mealGroupPurchaseRepository.findByIdIfExists(id)
                .orElseThrow(() -> new RuntimeException("해당 식사공동구매를 찾을 수 없습니다. ID: " + id));
        return MealGroupPurchaseResponseDto.from(purchase);
    }

    /**
     * 식사공동구매 등록
     */
    @Transactional
    public MealGroupPurchaseResponseDto createMealGroupPurchase(MealGroupPurchaseRequestDto requestDto) {
        // 유효성 검증
        validateMealGroupPurchaseRequest(requestDto);
        
        MealGroupPurchase purchase = MealGroupPurchase.builder()
                .creatorUserId(requestDto.getCreatorUserId())
                .restaurantId(requestDto.getRestaurantId())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .minOrderAmount(requestDto.getMinOrderAmount())
                .startAt(requestDto.getStartAt())
                .endAt(requestDto.getEndAt())
                .pickupTime(requestDto.getPickupTime())
                .recipientName(requestDto.getRecipientName())
                .recipientPhoneNumber(requestDto.getRecipientPhoneNumber())
                .deliveryAddress(requestDto.getDeliveryAddress())
                .deliveryRequest(requestDto.getDeliveryRequest())
                .build();

        MealGroupPurchase savedPurchase = mealGroupPurchaseRepository.save(purchase);
        return MealGroupPurchaseResponseDto.from(savedPurchase);
    }

    /**
     * 식사공동구매 수정
     */
    @Transactional
    public MealGroupPurchaseResponseDto updateMealGroupPurchase(Integer id, MealGroupPurchaseRequestDto requestDto) {
        MealGroupPurchase purchase = mealGroupPurchaseRepository.findByIdIfExists(id)
                .orElseThrow(() -> new RuntimeException("해당 식사공동구매를 찾을 수 없습니다. ID: " + id));

        // 유효성 검증
        validateMealGroupPurchaseRequest(requestDto);
        
        // 수정 권한 체크 (생성자만 수정 가능)
        if (!purchase.getCreatorUserId().equals(requestDto.getCreatorUserId())) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        // 엔티티 업데이트
        purchase.setTitle(requestDto.getTitle());
        purchase.setContent(requestDto.getContent());
        purchase.setMinOrderAmount(requestDto.getMinOrderAmount());
        purchase.setStartAt(requestDto.getStartAt());
        purchase.setEndAt(requestDto.getEndAt());
        purchase.setPickupTime(requestDto.getPickupTime());
        purchase.setRecipientName(requestDto.getRecipientName());
        purchase.setRecipientPhoneNumber(requestDto.getRecipientPhoneNumber());
        purchase.setDeliveryAddress(requestDto.getDeliveryAddress());
        purchase.setDeliveryRequest(requestDto.getDeliveryRequest());
        purchase.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        MealGroupPurchase updatedPurchase = mealGroupPurchaseRepository.save(purchase);
        return MealGroupPurchaseResponseDto.from(updatedPurchase);
    }

    /**
     * 식사공동구매 삭제
     */
    @Transactional
    public MealGroupPurchaseDeleteResponseDto deleteMealGroupPurchase(Integer id, Integer requestUserId) {
        MealGroupPurchase purchase = mealGroupPurchaseRepository.findByIdIfExists(id)
                .orElseThrow(() -> new RuntimeException("해당 식사공동구매를 찾을 수 없습니다. ID: " + id));

        // 삭제 권한 체크 (생성자만 삭제 가능)
        if (!purchase.getCreatorUserId().equals(requestUserId)) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        // 현재 참여자가 있는지 확인
        if (purchase.getCurrentMembers() > 0) {
            throw new RuntimeException("참여자가 있는 공동구매는 삭제할 수 없습니다.");
        }

        // 삭제 전 정보 저장
        String deletedTitle = purchase.getTitle();
        Integer deletedId = purchase.getMealGroupPurchaseId();

        mealGroupPurchaseRepository.delete(purchase);

        // 삭제 응답 생성
        return MealGroupPurchaseDeleteResponseDto.builder()
                .message("식사공동구매가 성공적으로 삭제되었습니다.")
                .deletedId(deletedId)
                .deletedTitle(deletedTitle)
                .build();
    }

    /**
     * 요청 데이터 유효성 검증
     */
    private void validateMealGroupPurchaseRequest(MealGroupPurchaseRequestDto requestDto) {
        if (requestDto.getTitle() == null || requestDto.getTitle().trim().isEmpty()) {
            throw new RuntimeException("제목은 필수입니다.");
        }
        
        if (requestDto.getCreatorUserId() == null) {
            throw new RuntimeException("생성자 ID는 필수입니다.");
        }
        
        if (requestDto.getRestaurantId() == null) {
            throw new RuntimeException("식당 ID는 필수입니다.");
        }
        
        if (requestDto.getStartAt() == null || requestDto.getEndAt() == null) {
            throw new RuntimeException("시작시간과 종료시간은 필수입니다.");
        }
        
        if (requestDto.getStartAt().after(requestDto.getEndAt())) {
            throw new RuntimeException("시작시간은 종료시간보다 빨라야 합니다.");
        }
        
        if (requestDto.getRecipientName() == null || requestDto.getRecipientName().trim().isEmpty()) {
            throw new RuntimeException("수령자 이름은 필수입니다.");
        }
        
        if (requestDto.getRecipientPhoneNumber() == null || requestDto.getRecipientPhoneNumber().trim().isEmpty()) {
            throw new RuntimeException("수령자 전화번호는 필수입니다.");
        }
        
        if (requestDto.getDeliveryAddress() == null || requestDto.getDeliveryAddress().trim().isEmpty()) {
            throw new RuntimeException("배송 주소는 필수입니다.");
        }
    }
}
