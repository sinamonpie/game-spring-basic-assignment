package com.gamebasic.ranking.service;

import com.gamebasic.game.entity.GameStatus;
import com.gamebasic.ranking.RankingClient;
import com.gamebasic.ranking.dto.Ranking;
import com.gamebasic.ranking.dto.RankingResponse;
import com.gamebasic.ranking.dto.RankingSource;
import com.gamebasic.runcard.entity.CardType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RankingService {
    private final RankingClient rankingClient;

    @Transactional(readOnly = true)
    public RankingResponse getRankings() {
        RankingSource source = rankingClient.fetch();
        if(source == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        List<RankingSource.Record> validRecords = new ArrayList<>();
        int excludedCount = 0;

        for(RankingSource.Record record : source.getRecords()) {
            if(!isTargetRank(record.getRun()))
                continue;

            if(isValidRecord(record))
                validRecords.add(record);
            else
                excludedCount++;
        }
        if(validRecords.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        validRecords.sort(Comparator
                .comparing((RankingSource.Record r) -> r.getRun().getDurationSeconds())
                .thenComparing(r->r.getRun().getFinalHp(), Comparator.reverseOrder())
                .thenComparing(RankingSource.Record::getId));

        Set<String> playerIds = new HashSet<>();
        List<Ranking> rankings = new ArrayList<>();

        int rank = 1;
        for(RankingSource.Record record : validRecords) {
            if(playerIds.contains(record.getPlayer().getId()))
                continue;
            Ranking ranking = new Ranking(rank, record);
            rankings.add(ranking);
            playerIds.add(record.getPlayer().getId());
            rank++;
        }

        return new RankingResponse(source, excludedCount, rankings);
    }

    // 순위 대상 여부
    public boolean isTargetRank (RankingSource.Run run) {
        try {
            return run.getStatus().equals(GameStatus.CLEARED.name()) && run.getClearedFloor() == 10;
        } catch (NullPointerException e) {
            return false;
        }
    }

    // 정상 기록 검증
    public boolean isValidRecord(RankingSource.Record record) {
        return isValidRun(record.getRun()) && isValidBossFight(record.getBossFight(), isValidDeck(record.getDeck()));
    }

    public boolean isValidRun(RankingSource.Run run) {
        try {
            // 클리어 시간 조건
            if (run.getDurationSeconds() < run.getClearedFloor() * 30L)
                return false;

            // 남은 HP 조건
            return run.getFinalHp() > 0 && run.getFinalHp() < 100;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public List<String> isValidDeck(RankingSource.Deck deck) {
        try {
            List<String> result = new ArrayList<>();

            // 덱 크기
            if (deck.getCards().size() != deck.getSize() || deck.getSize() < 9 || deck.getSize() > 20)
                return null;

            for (RankingSource.Card card : deck.getCards()) {
                // 카드 타입
                if (card.getCardType() == null || !isValidCardType(card.getCardType()))
                    return null;

                // 획득 층
                if(card.getAcquiredFloor() < 0 || card.getAcquiredFloor() > 9)
                    return null;

                result.add(card.getCardType());
            }

            return result;
        } catch (NullPointerException e) {
            return null;
        }
    }

    // 카드 타입 검증
    public boolean isValidCardType(String cardType) {
        try {
            CardType.valueOf(cardType);
            return true;
        } catch (IllegalArgumentException |  NullPointerException e) {
            return false;
        }
    }

    public boolean isValidBossFight(RankingSource.BossFight bossFight, List<String> cardTypes) {
        try {
            if(cardTypes == null || bossFight.getPhases().size() != 3)
                return false;

            // 보스 페이즈
            List<String> expectedPhases = List.of("THRONE", "UNBOUND", "ECLIPSE");

            int sumTunrs = 0;
            for(int i = 0; i < expectedPhases.size(); i++) {
                if(!bossFight.getPhases().get(i).getPhase().equals(expectedPhases.get(i)) || bossFight.getPhases().get(i).getTurns() < 1)
                    return false;

                sumTunrs += bossFight.getPhases().get(i).getTurns();
            }

            if(bossFight.getTotalTurns() != sumTunrs)
                return false;

            // 마무리 카드
            if(!cardTypes.contains(bossFight.getFinishingCard()))
                return false;

            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }
}
