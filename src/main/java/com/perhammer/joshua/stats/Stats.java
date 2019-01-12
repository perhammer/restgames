package com.perhammer.joshua.stats;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data @NoArgsConstructor
@Entity
public class Stats {
    private @Id @GeneratedValue Long id;
    private Class gameClass;
    private Integer gamesAttempted = 0;
    private Integer gamesCompleted = 0;
    private Long shortestSuccessfulGame = Long.MAX_VALUE;
    private Long longestSuccessfulGame = 0L;
    private Integer activeGames = 0;
    private Integer highestActiveGames = 0;
    private Long highestActiveGameTimestampMillis = 0L;
}
