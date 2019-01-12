package com.perhammer.joshua.scores;

import com.perhammer.joshua.registration.Registration;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ScoreService {

    private final ScoreRepository repository;

    public ScoreService(ScoreRepository repository) {
        this.repository = repository;
    }

    public List<Score> getAllByGame(Class game) {
        return Collections.emptyList();
    }

    public List<Score> getAllByRegistration(Registration registration) {
        return Collections.emptyList();
    }

    public List<Score> getAllByRegistrationAndGame(Registration registration, Class game) {
        return Collections.emptyList();
    }

    public void submitMoves(Registration registration, String scoredInGameVariation, Long moves, Long duration) {
        Score score = new Score();
        score.setScoredByRegistrationId(registration.getId());
        score.setGameVariation(scoredInGameVariation);
        score.setMoves(moves);
        score.setTrackedDurationMillis(duration);

        repository.save( score );
    }

    public void submitScore(Registration registration, String scoredInGameVariation, int points, Long duration) {
        Score score = new Score();
        score.setScoredByRegistrationId(registration.getId());
        score.setGameVariation(scoredInGameVariation);
        score.setPoints(points);
        score.setTrackedDurationMillis(duration);

        repository.save(score);
    }
}
