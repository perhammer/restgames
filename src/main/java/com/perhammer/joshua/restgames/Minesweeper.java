package com.perhammer.joshua.restgames;

import com.perhammer.joshua.minesweeper.*;
import com.perhammer.joshua.registration.RegistrationRepository;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class Minesweeper {

    private final MinesweeperStateResourceAssembler assembler;
    private final RegistrationRepository repository;
    private final Map<Long, MinesweeperController> liveGames;
    private final Random random = new Random();

    public Minesweeper(RegistrationRepository repository, MinesweeperStateResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;

        this.liveGames = new HashMap<>();
    }

    @GetMapping(value = "/minesweeper/new/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<MinesweeperState> newGame(@PathVariable("id") Long gameId) {
        cleanUp(gameId);

        return newGame();
    }


    @GetMapping(value = "/minesweeper/new", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<MinesweeperState> newGame() {
        MinesweeperBoardModel mbm = new MinesweeperBoardModelFactory().getBeginnerBoard();
        MinesweeperController mc = new MinesweeperController(mbm);

        Long gameId = random.nextLong();

        liveGames.put(gameId, mc);

        MinesweeperState state = new MinesweeperState(gameId);
        state.setBoard(mbm);

        return assembler.toResource(state);
    }

    @GetMapping(value = "/minesweeper/{id}/reveal/{x}/{y}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<MinesweeperState> reveal(@PathVariable("id") Long gameId, @PathVariable("x") int x, @PathVariable("y") int y) {
        MinesweeperController mc = getMinesweeperController(gameId);

        MinesweeperState state = new MinesweeperState(gameId);
        state.setBoard(mc.getBoard());

        if (xAndYareValid(x, y, mc)) {
            try {
                mc.reveal(x, y);
            } catch (MinesweeperGameLostException | MinesweeperGameWonException e) {
                cleanUp(gameId);
                state.setFinished(true);
            }
        }

        return assembler.toResource(state);
    }

    @GetMapping(value = "/minesweeper/{id}/mark/{x}/{y}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<MinesweeperState> mark(@PathVariable("id") Long gameId, @PathVariable("x") int x, @PathVariable("y") int y) {
        MinesweeperController mc = getMinesweeperController(gameId);

        MinesweeperState state = new MinesweeperState(gameId);
        state.setBoard(mc.getBoard());

        if (xAndYareValid(x, y, mc)) {
            mc.mark(x, y);
        }

        return assembler.toResource(state);
    }

    @GetMapping(value = "/minesweeper/{id}/unmark/{x}/{y}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<MinesweeperState> unmark(@PathVariable("id") Long gameId, @PathVariable("x") int x, @PathVariable("y") int y) {
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

    private MinesweeperController getMinesweeperController(@PathVariable("id") Long gameId) {
        MinesweeperController mc = liveGames.get(gameId);

        if (mc == null) {
            throw new GameNotFoundException("Game not found: " + gameId);
        }
        return mc;
    }

    private void cleanUp(Long gameId) {
        liveGames.remove(gameId);
    }
}
