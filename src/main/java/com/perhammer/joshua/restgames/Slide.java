package com.perhammer.joshua.restgames;

import com.perhammer.joshua.registration.RegistrationRepository;
import com.perhammer.joshua.slidingpuzzle.SlidingMove;
import com.perhammer.joshua.slidingpuzzle.SlidingPuzzleController;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class Slide {

    private final RegistrationRepository repository;
    private final SlideStateResourceAssembler assembler;
    private Map<Long, SlidingPuzzleController> liveGames;
    private final Random random = new Random();

    public Slide(RegistrationRepository repository, SlideStateResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
        this.liveGames = new HashMap<>();
    }

    @GetMapping(value = "/slide/new/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<SlideState> newGame(@PathVariable Long id) {
        cleanUp(id);

        return newGame();
    }

    @GetMapping(value = "/slide/new", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<SlideState> newGame() {
        SlidingPuzzleController spc = SlidingPuzzleController.get4by4game();
        Long id = random.nextLong();

        liveGames.put(id, spc);

        spc.scramble();

        SlideState state = new SlideState(id, spc.getBoard());

        state.setMoves(spc.getValidMoves());

        return assembler.toResource(state);
    }

    @GetMapping(value = "/slide/{id}/up", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<SlideState> up(@PathVariable Long id) {
        return tryMove(id, SlidingMove.NORTH);
    }

    @GetMapping(value = "/slide/{id}/down", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<SlideState> down(@PathVariable Long id) {
        return tryMove(id, SlidingMove.SOUTH);
    }

    @GetMapping(value = "/slide/{id}/left", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<SlideState> left(@PathVariable Long id) {
        return tryMove(id, SlidingMove.WEST);
    }

    @GetMapping(value = "/slide/{id}/right", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<SlideState> right(@PathVariable Long id) {
        return tryMove(id, SlidingMove.EAST);
    }

    private Resource<SlideState> tryMove(Long gameId, SlidingMove direction) {
        SlidingPuzzleController spc = liveGames.get(gameId);

        if (spc==null) {
            throw new GameNotFoundException("Game not found: "+gameId);
        }

        spc.slide(direction.getOpposite());

        SlideState state = new SlideState(gameId, spc.getBoard() );
        state.setMoves( spc.getValidMoves() );

        if(spc.isFinished()) {
            state.setMoves(Collections.emptyList());

            cleanUp(gameId);
        }

        return assembler.toResource(state);
    }

    private void cleanUp(Long gameId) {
        liveGames.remove(gameId);
    }
}
