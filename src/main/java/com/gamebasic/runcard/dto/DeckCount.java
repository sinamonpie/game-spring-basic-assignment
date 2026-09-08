package com.gamebasic.runcard.dto;

import com.gamebasic.game.entity.Game;
import lombok.Getter;

@Getter
public class DeckCount {
    private final Game game;
    private final Integer count;

    public DeckCount(Game game, Long count) {
        this.game = game;
        this.count = count != null ? count.intValue() : 0;
    }
}
