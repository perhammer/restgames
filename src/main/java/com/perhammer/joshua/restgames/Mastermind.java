package com.perhammer.joshua.restgames;

import com.perhammer.joshua.mastermind.MastermindColour;
import com.perhammer.joshua.mastermind.MastermindController;
import com.perhammer.joshua.mastermind.MastermindHint;
import com.perhammer.joshua.registration.Registration;
import com.perhammer.joshua.registration.RegistrationRepository;
import com.perhammer.joshua.scores.ScoreRepository;
import com.perhammer.joshua.scores.ScoreService;
import com.perhammer.joshua.slidingpuzzle.SlidingPuzzleController;
import com.perhammer.joshua.stats.StatsService;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class Mastermind {

    private static final Class CLASS_FOR_STATS = Mastermind.class;

    private final RegistrationRepository repository;
    private final MastermindStateResourceAssembler assembler;
    private final Map<Integer, MastermindController> liveGames;
    private final Random random = new Random();
    private final StatsService statsService;
    private final ScoreService scoreService;

    public Mastermind(RegistrationRepository repository, MastermindStateResourceAssembler assembler, StatsService statsService, ScoreService scoreService) {
        this.repository = repository;
        this.assembler = assembler;
        this.statsService = statsService;
        this.scoreService = scoreService;
        this.liveGames = new HashMap<>();
    }


    @GetMapping(value = "/mastermind/new/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<MastermindState> newGame(@PathVariable("id") Integer id) {
        MastermindController mc = liveGames.get(id);

        if (mc!=null) {
            statsService.gameAbandoned(CLASS_FOR_STATS);
            cleanUp(id);
        }

        return newGame();
    }

    @GetMapping(value = "/mastermind/new", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<MastermindState> newGame() {
        MastermindController mc = new MastermindController();

        statsService.gameStarted(CLASS_FOR_STATS);

        int gameId = mc.hashCode();
        liveGames.put(gameId, mc);

        MastermindState state = new MastermindState(gameId);

        return assembler.toResource( state );
    }

    @GetMapping(value = "/mastermind/rejoin/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<MastermindState> rejoinGame(@PathVariable("id") Integer id) {
        MastermindController mc = liveGames.get(id);

        if (mc==null) {
            throw new GameNotFoundException("Game not found: "+id);
        }

        MastermindState state = new MastermindState(id);

        return assembler.toResource(state);
    }

    @GetMapping(value = "/mastermind/{id}/guess/{guess}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<MastermindState> makeGuess(@PathVariable("id") Integer gameId, @PathVariable("guess") String guess) {
        MastermindController mc = liveGames.get(gameId);

        if (mc==null) {
            throw new GameNotFoundException("Game not found: "+gameId);
        }

        MastermindState state = new MastermindState(gameId);

        List<MastermindColour> attempt = getAttempt(mc.getBoard().getChallengeWidth(), guess);

        List<MastermindHint> hint =  mc.addAttempt(attempt);

        state.setHint(hint);

        if(mc.isFinished()) {
            statsService.offerSuccessfulGameDuration(CLASS_FOR_STATS, mc.getGameDuration());
            statsService.gameFinished(CLASS_FOR_STATS);

            Registration player = repository.findByTeamname( SecurityContextHolder.getContext().getAuthentication().getName() );
            scoreService.submitMoves(player, mc.getGameVariation(), mc.getMoveCount(), mc.getGameDuration());

            cleanUp(gameId);

            String code = "";
            for(MastermindColour colour:attempt) {
                code = code + (colour+"").charAt(0);
            }
            state.setCode(code);
            state.setFinished(true);
        }

        return assembler.toResource(state);
    }

    private void cleanUp(Integer gameId) {
        liveGames.remove(gameId);
    }

    private List<MastermindColour> getAttempt(int challengeWidth, String guess) {
        List<MastermindColour> attempt = new ArrayList<>();

        if (guess.length()>challengeWidth) {
            guess = guess.substring(0,challengeWidth);
        }

        for(int i=0; i!=guess.length(); i++) {
            attempt.add(MastermindColour.fromString(guess.charAt(i)));
        }
        return attempt;
    }

}
