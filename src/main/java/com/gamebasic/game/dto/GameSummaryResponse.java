package com.gamebasic.game.dto;

import com.gamebasic.game.entity.BaseEntity;
import com.gamebasic.game.entity.Game;
import com.gamebasic.game.entity.GamePhase;
import com.gamebasic.game.entity.GameStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GameSummaryResponse {
    private final Long id;
    private final String playerName;
    private final Integer currentFloor;
    private final Integer currentHp;
    private final GamePhase phase;
    private final GameStatus status;
    private final Integer deckSize;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public GameSummaryResponse(Game game, Integer deckSize) {
        this.id = game.getId();
        this.playerName = game.getPlayerName();
        this.currentFloor = game.getCurrentFloor();
        this.currentHp = game.getCurrentHp();
        this.phase = game.getPhase();
        this.status = game.getStatus();
        this.deckSize = deckSize;
        this.createdAt = game.getCreatedAt();
        this.updatedAt = game.getUpdatedAt();
    }
}
