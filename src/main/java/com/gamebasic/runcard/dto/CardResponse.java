package com.gamebasic.runcard.dto;

import com.gamebasic.runcard.entity.RunCard;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CardResponse {
    // TODO (Lv 5): API 명세의 카드 응답 JSON에 맞게 필드를 만들고 생성자에서 채우세요.
    private final Long id;
    private final String cardType;
    private final Integer acquiredFloor;

    public CardResponse(RunCard card) {
        this.id = card.getId();
        this.cardType = card.getCardType();
        this.acquiredFloor = card.getAcquiredFloor();
    }

    public CardResponse(Long id, String cardType, int acquiredFloor) {
        this.id = id;
        this.cardType = cardType;
        this.acquiredFloor = acquiredFloor;
    }
}
