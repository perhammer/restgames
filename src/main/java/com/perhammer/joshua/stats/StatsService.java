package com.perhammer.joshua.stats;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatsService {

    private final StatsRepository repository;
    private final Map<Class, Stats> inMemRepo;

    public StatsService(StatsRepository repository) {
        this.repository = repository;
        this.inMemRepo = new HashMap<>();
    }

    private Stats getStatsForGame(Class gameClass) {
        Stats stats = inMemRepo.get(gameClass);

        if (stats==null) {
            stats = new Stats();
            stats.setGameClass(gameClass);
            inMemRepo.put(gameClass, stats);
            repository.save(stats);
        }

        return stats;
    }

    public void offerSuccessfulGameDuration(Class gameClass, Long durationMillis) {
        // shortest and longest
        boolean saveRequired = false;
        Stats stats = getStatsForGame(gameClass);

        if(durationMillis>stats.getLongestSuccessfulGame()) {
            stats.setLongestSuccessfulGame( durationMillis );
            saveRequired = true;
        }
        if (durationMillis<stats.getShortestSuccessfulGame()) {
            stats.setShortestSuccessfulGame(durationMillis);
            saveRequired = true;
        }

        if (saveRequired) {
            repository.save(stats);
        }
    }

    public void gameStarted(Class gameClass) {
        Stats stats = getStatsForGame(gameClass);
        stats.setGamesAttempted( stats.getGamesAttempted()+1 );
        stats.setActiveGames( stats.getActiveGames()+1 );
        if(stats.getActiveGames()>stats.getHighestActiveGames()) {
            stats.setHighestActiveGames( stats.getActiveGames() );
            stats.setHighestActiveGameTimestampMillis( System.currentTimeMillis() );
        }
        repository.save(stats);
    }

    public void gameFinished(Class gameClass) {
        gameFinished(gameClass, true);
    }

    public void gameAbandoned(Class gameClass) {
        gameFinished(gameClass, false);
    }

    private void gameFinished(Class gameClass, boolean gameFinishedSuccessfully) {
        Stats stats = getStatsForGame(gameClass);
        int activeGames = stats.getActiveGames();
        if (activeGames>0) {
            stats.setActiveGames( activeGames-1 );
        }
        if (gameFinishedSuccessfully) {
            stats.setGamesCompleted( stats.getGamesCompleted()+1 );
        }
        repository.save(stats);
    }

    public List<Stats> getStats() {
        return repository.findAll();
    }
}
