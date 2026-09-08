package com.gamebasic.ranking.dto;

import lombok.Getter;

@Getter
public class Ranking {
    private final Integer rank;
    private final String playerName;
    private final Integer clearTimeSeconds;
    private final Integer remainingHp;
    private final Integer bossTurns;
    private final Integer deckSize;

    public Ranking(Integer rank, RankingSource.Record record) {
        this.rank = rank;
        this.playerName = record.getPlayer().getName();
        this.clearTimeSeconds = record.getRun().getDurationSeconds().intValue();
        this.remainingHp = record.getRun().getFinalHp();
        this.bossTurns = record.getBossFight().getTotalTurns();
        this.deckSize = record.getDeck().getSize();
    }
}
