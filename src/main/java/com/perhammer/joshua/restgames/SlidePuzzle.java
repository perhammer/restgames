package com.perhammer.joshua.restgames;

import com.perhammer.joshua.registration.Registration;
import com.perhammer.joshua.registration.RegistrationRepository;
import com.perhammer.joshua.scores.ScoreService;
import com.perhammer.joshua.slidingpuzzle.SlidingMove;
import com.perhammer.joshua.slidingpuzzle.SlidingPuzzleController;
import com.perhammer.joshua.slidingpuzzle.SlidingPuzzleControllerFactory;
import com.perhammer.joshua.stats.StatsService;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class SlidePuzzle {

    private static final Class CLASS_FOR_STATS = SlidePuzzle.class;

    private final RegistrationRepository repository;
    private final SlideStateResourceAssembler assembler;
    private final StatsService statsService;
    private final ScoreService scoreService;
    private Map<Integer, SlidingPuzzleController> liveGames;

    public SlidePuzzle(RegistrationRepository repository, SlideStateResourceAssembler assembler, StatsService statsService, ScoreService scoreService) {
        this.repository = repository;
        this.assembler = assembler;
        this.statsService = statsService;
        this.scoreService = scoreService;
        this.liveGames = new HashMap<>();
    }

    @GetMapping(value = "/slide/new/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<SlideState> newGame(@PathVariable("id") Integer id) {
        SlidingPuzzleController spc = liveGames.get(id);

        if (spc!=null) {
            statsService.gameAbandoned(CLASS_FOR_STATS);
            cleanUp(id);
        }

        return newGame();
    }

    @GetMapping(value = "/slide/rejoin/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<SlideState> rejoinGame(@PathVariable("id") Integer gameId) {
        SlidingPuzzleController spc = liveGames.get(gameId);

        if (spc==null) {
            throw new GameNotFoundException("Game not found: "+gameId);
        }

        SlideState state = new SlideState(gameId, spc.getBoard() );
        state.setMoves( spc.getValidMoves() );

        return assembler.toResource(state);
    }

    @GetMapping(value = "/slide/new", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<SlideState> newGame() {
        SlidingPuzzleController spc = SlidingPuzzleControllerFactory.getFactory().get4by4game();

        statsService.gameStarted(CLASS_FOR_STATS);

        Integer gameId = spc.hashCode();

        liveGames.put(gameId, spc);

        spc.scramble();

        SlideState state = new SlideState(gameId, spc.getBoard());

        state.setMoves(spc.getValidMoves());

        return assembler.toResource(state);
    }

    @GetMapping(value = "/slide/{id}/up", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<SlideState> up(@PathVariable("id") Integer id) {
        return tryMove(id, SlidingMove.NORTH);
    }

    @GetMapping(value = "/slide/{id}/down", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<SlideState> down(@PathVariable("id") Integer id) {
        return tryMove(id, SlidingMove.SOUTH);
    }

    @GetMapping(value = "/slide/{id}/left", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<SlideState> left(@PathVariable("id") Integer id) {
        return tryMove(id, SlidingMove.WEST);
    }

    @GetMapping(value = "/slide/{id}/right", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<SlideState> right(@PathVariable("id") Integer id) {
        return tryMove(id, SlidingMove.EAST);
    }

    private Resource<SlideState> tryMove(Integer gameId, SlidingMove direction) {
        SlidingPuzzleController spc = liveGames.get(gameId);

        if (spc==null) {
            throw new GameNotFoundException("Game not found: "+gameId);
        }

        spc.slide(direction.getOpposite());

        SlideState state = new SlideState(gameId, spc.getBoard() );
        state.setMoves( spc.getValidMoves() );

        if(spc.isFinished()) {
            state.setMoves(Collections.emptyList());

            statsService.offerSuccessfulGameDuration(CLASS_FOR_STATS, spc.getGameDuration());
            statsService.gameFinished(CLASS_FOR_STATS);

            Registration player = repository.findByTeamname( SecurityContextHolder.getContext().getAuthentication().getName() );
            scoreService.submitMoves(player, spc.getGameVariation(), spc.getMoveCount(), spc.getGameDuration());

            cleanUp(gameId);
        }

        return assembler.toResource(state);
    }

    private void cleanUp(Integer gameId) {
        liveGames.remove(gameId);
    }
}
