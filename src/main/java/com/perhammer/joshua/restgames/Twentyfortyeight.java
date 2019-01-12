package com.perhammer.joshua.restgames;

import com.perhammer.joshua.registration.Registration;
import com.perhammer.joshua.registration.RegistrationRepository;
import com.perhammer.joshua.scores.ScoreService;
import com.perhammer.joshua.stats.StatsService;
import com.perhammer.joshua.twentyfortyeight.Twenty48Controller;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class Twentyfortyeight {

    private static final Class CLASS_FOR_STATS = Twentyfortyeight.class;

    private final HashMap<Integer, Twenty48Controller> liveGames;
    private final TwentyfortyeightResourceAssembler assembler;
    private final StatsService statsService;
    private final RegistrationRepository repository;
    private final ScoreService scoreService;

    public Twentyfortyeight(RegistrationRepository repository, TwentyfortyeightResourceAssembler assembler, StatsService statsService, ScoreService scoreService) {
        this.repository = repository;
        this.assembler = assembler;
        this.statsService = statsService;
        this.scoreService = scoreService;

        this.liveGames = new HashMap<>();
    }

    @GetMapping(value = "/2048/{id}/new", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<TwentyfortyeightState> rejoinGame(@PathVariable("id") Integer gameId) {
        Twenty48Controller tc = getController(gameId);


        return newGame();
    }

    @GetMapping(value = "/2048/rejoin/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<TwentyfortyeightState> newGame(@PathVariable("id") Integer gameId) {
        Twenty48Controller tc = liveGames.get(gameId);

        TwentyfortyeightState state = getGameState(tc.hashCode());

        return assembler.toResource( state );
    }

    @GetMapping(value = "/2048/new", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<TwentyfortyeightState> newGame() {
        Twenty48Controller controller = new Twenty48Controller();

        statsService.gameStarted(CLASS_FOR_STATS);

        this.liveGames.put(controller.hashCode(), controller);

        TwentyfortyeightState state = getGameState(controller.hashCode());

        return assembler.toResource( state );
    }

    @GetMapping(value = "/2048/{id}/up", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<TwentyfortyeightState> slideUp(@PathVariable("id") Integer gameId) {
        Twenty48Controller controller = getController(gameId);

        controller.slideUp();

        TwentyfortyeightState state = getGameState(gameId);

        cleanUpIfGameIsOver(gameId);

        return assembler.toResource( state );
    }

    @GetMapping(value = "/2048/{id}/down", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<TwentyfortyeightState> slideDown(@PathVariable("id") Integer gameId) {
        Twenty48Controller controller = getController(gameId);

        controller.slideDown();

        TwentyfortyeightState state = getGameState(gameId);

        cleanUpIfGameIsOver(gameId);

        return assembler.toResource( state );
    }

    @GetMapping(value = "/2048/{id}/left", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<TwentyfortyeightState> slideLeft(@PathVariable("id") Integer gameId) {
        Twenty48Controller controller = getController(gameId);

        controller.slideLeft();

        TwentyfortyeightState state = getGameState(gameId);

        cleanUpIfGameIsOver(gameId);

        return assembler.toResource( state );
    }

    @GetMapping(value = "/2048/{id}/right", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<TwentyfortyeightState> slideRight(@PathVariable("id") Integer gameId) {
        Twenty48Controller controller = getController(gameId);

        controller.slideRight();

        TwentyfortyeightState state = getGameState(gameId);

        cleanUpIfGameIsOver(gameId);

        return assembler.toResource( state );
    }

    private Twenty48Controller getController(Integer gameId) {
        Twenty48Controller controller = this.liveGames.get(gameId);
        if (controller==null) {
            throw new GameNotFoundException("Game not found: "+gameId);
        }
        return controller;
    }

    private TwentyfortyeightState getGameState(Integer gameId) {
        Twenty48Controller controller = getController(gameId);

        TwentyfortyeightState state = new TwentyfortyeightState();

        state.setGameId(gameId);
        state.setFinished(controller.isFinished());
        state.setScore(controller.getScore());
        state.setBoard(controller.getBoard());

        return state;
    }

    private void cleanUpIfGameIsOver(Integer gameId) {
        Twenty48Controller controller = getController(gameId);

        if (controller.isFinished()) {
            statsService.offerSuccessfulGameDuration(CLASS_FOR_STATS, controller.getGameDuration());
            statsService.gameFinished(CLASS_FOR_STATS);

            Registration player = repository.findByTeamname( SecurityContextHolder.getContext().getAuthentication().getName() );
            scoreService.submitScore(player, controller.getGameVariation(), controller.getScore(), controller.getGameDuration());

            this.liveGames.remove(gameId);
        }
    }
}
