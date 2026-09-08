package com.gamebasic.ranking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class RankingSource {
    private Meta meta;
    private List<Record> records;

    @Getter
    @NoArgsConstructor
    public static class Meta {
        private Season season;
        private OffsetDateTime generatedAt;
        private Integer schemaVersion;
        private Integer totalRecords;
    }

    @Getter
    @NoArgsConstructor
    public static class Season {
        private String id;
        private String name;
        private OffsetDateTime startsAt;
        private OffsetDateTime endsAt;
    }

    @Getter
    @NoArgsConstructor
    public static class Record {
        private Long id;
        private OffsetDateTime submittedAt;
        private Client client;
        private Player player;
        private Run run;
        private BossFight bossFight;
        private Deck deck;
    }

    @Getter
    @NoArgsConstructor
    public static class Client {
        private String version;
        private String platform;
        private String locale;
    }

    @Getter
    @NoArgsConstructor
    public static class Player {
        private String id;
        private String name;
        private String region;
        private List<String> tags;
    }

    @Getter
    @NoArgsConstructor
    public static class Run {
        private String seed;
        private String status;
        private Integer clearedFloor;
        private Long durationSeconds;
        private Integer finalHp;
        private List<Floor> floors;
    }

    @Getter
    @NoArgsConstructor
    public static class Floor {
        private Integer floor;
        private String enemy;
        private Integer turns;
        private Integer hpAfter;
        private List<Reword> rewards;
    }

    @Getter
    @NoArgsConstructor
    public static class Reword {
        private List<String> offered;
        private String picked;
    }

    @Getter
    @NoArgsConstructor
    public static class BossFight {
        private List<Phase> phases;
        private String finishingCard;
        private Integer totalTurns;
    }

    @Getter
    @NoArgsConstructor
    public static class Phase {
        private String phase;
        private Integer turns;
        private Integer damageTaken;
    }

    @Getter
    @NoArgsConstructor
    public static class Deck {
        private Integer size;
        private List<Card> cards;
    }

    @Getter
    @NoArgsConstructor
    public static class Card {
        private String cardType;
        private Integer acquiredFloor;
    }
}
