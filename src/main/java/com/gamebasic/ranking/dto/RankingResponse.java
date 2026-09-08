package com.gamebasic.ranking.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class RankingResponse {
    private final String season;
    private final Integer totalRecords;
    private final Integer excludedCount;
    private final List<Ranking> entries;

    public RankingResponse(RankingSource source, Integer excludedCount, List<Ranking> entries) {
        this.season = source.getMeta().getSeason().getId();
        this.totalRecords = source.getMeta().getTotalRecords();
        this.excludedCount = excludedCount;
        this.entries = entries;
    }
}
