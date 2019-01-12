package com.perhammer.joshua.restgames;

import com.perhammer.joshua.minesweeper.*;
import com.perhammer.joshua.registration.Registration;
import com.perhammer.joshua.registration.RegistrationRepository;
import com.perhammer.joshua.scores.ScoreService;
import com.perhammer.joshua.stats.StatsService;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class Minesweeper {

    private static final Class CLASS_FOR_STATS = Minesweeper.class;

    private final MinesweeperStateResourceAssembler assembler;
    private final RegistrationRepository repository;
    private final Map<Integer, MinesweeperController> liveGames;
    private final StatsService statsService;
    private final ScoreService scoreService;

    public Minesweeper(RegistrationRepository repository, MinesweeperStateResourceAssembler assembler, StatsService statsService, ScoreService scoreService) {
        this.repository = repository;
        this.assembler = assembler;
        this.statsService = statsService;
        this.scoreService = scoreService;

        this.liveGames = new HashMap<>();
    }

    @GetMapping(value = "/minesweeper/new/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<MinesweeperState> newGame(@PathVariable("id") Integer gameId) {
        MinesweeperController mc = liveGames.get(gameId);
        if (mc!=null) {
            statsService.gameAbandoned(CLASS_FOR_STATS);
            cleanUp(gameId);
        }

        return newGame();
    }

    @GetMapping(value = "/minesweeper/rejoin/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<MinesweeperState> rejoinGame(@PathVariable("id") Integer gameId) {
        MinesweeperController mc = getMinesweeperController(gameId);

        MinesweeperState state = new MinesweeperState(gameId);
        state.setBoard(mc.getBoard());

        return newGame();
    }

    @GetMapping(value = "/minesweeper/new", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<MinesweeperState> newGame() {
        MinesweeperBoardModel mbm = new MinesweeperBoardModelFactory().getBeginnerBoard();
        MinesweeperController mc = new MinesweeperController(mbm);

        statsService.gameStarted(CLASS_FOR_STATS);

        Integer gameId = mc.hashCode();

        liveGames.put(gameId, mc);

        MinesweeperState state = new MinesweeperState(gameId);
        state.setBoard(mbm);

        return assembler.toResource(state);
    }

    @GetMapping(value = "/minesweeper/{id}/reveal/{x}/{y}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<MinesweeperState> reveal(@PathVariable("id") Integer gameId, @PathVariable("x") int x, @PathVariable("y") int y) {
        MinesweeperController mc = getMinesweeperController(gameId);

        MinesweeperState state = new MinesweeperState(gameId);
        state.setBoard(mc.getBoard());

        if (xAndYareValid(x, y, mc)) {
            try {
                mc.reveal(x, y);
            } catch (MinesweeperGameLostException | MinesweeperGameWonException e) {
                statsService.offerSuccessfulGameDuration(CLASS_FOR_STATS, mc.getGameDuration());
                statsService.gameFinished(CLASS_FOR_STATS);

                Registration player = repository.findByTeamname( SecurityContextHolder.getContext().getAuthentication().getName() );
                scoreService.submitMoves(player, mc.getGameVariation(), mc.getMoveCount(), mc.getGameDuration());

                cleanUp(gameId);
                state.setFinished(true);
            }
        }

        return assembler.toResource(state);
    }

    @GetMapping(value = "/minesweeper/{id}/mark/{x}/{y}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<MinesweeperState> mark(@PathVariable("id") Integer gameId, @PathVariable("x") int x, @PathVariable("y") int y) {
        MinesweeperController mc = getMinesweeperController(gameId);

        MinesweeperState state = new MinesweeperState(gameId);
        state.setBoard(mc.getBoard());

        if (xAndYareValid(x, y, mc)) {
            mc.mark(x, y);
        }

        return assembler.toResource(state);
    }

    @GetMapping(value = "/minesweeper/{id}/unmark/{x}/{y}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<MinesweeperState> unmark(@PathVariable("id") Integer gameId, @PathVariable("x") int x, @PathVariable("y") int y) {
        MinesweeperController mc = getMinesweeperController(gameId);

        MinesweeperState state = new MinesweeperState(gameId);
        state.setBoard(mc.getBoard());

        if (xAndYareValid(x, y, mc)) {
            mc.unmark(x, y);
        }

        return assembler.toResource(state);
    }

    private boolean xAndYareValid(int x, int y, MinesweeperController mc) {
        return x >= 0 && x < mc.getBoard().getWidth() && y >= 0 && y < mc.getBoard().getHeight();
    }

    private MinesweeperController getMinesweeperController(Integer gameId) {
        MinesweeperController mc = liveGames.get(gameId);

        if (mc == null) {
            throw new GameNotFoundException("Game not found: " + gameId);
        }
        return mc;
    }

    private void cleanUp(Integer gameId) {
        liveGames.remove(gameId);
    }
}
