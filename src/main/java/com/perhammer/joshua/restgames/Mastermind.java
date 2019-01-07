package com.perhammer.joshua.restgames;

import com.perhammer.joshua.mastermind.MastermindColour;
import com.perhammer.joshua.mastermind.MastermindController;
import com.perhammer.joshua.mastermind.MastermindHint;
import com.perhammer.joshua.registration.RegistrationRepository;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class Mastermind {

    private final RegistrationRepository repository;
    private final MastermindStateResourceAssembler assembler;
    private final Map<Long, MastermindController> liveGames;
    private final Random random = new Random();

    public Mastermind(RegistrationRepository repository, MastermindStateResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
        this.liveGames = new HashMap<>();
    }


    @GetMapping(value = "/mastermind/new/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<MastermindState> newGame(@PathVariable Long id) {
        cleanUp(id);

        return newGame();
    }

    @GetMapping(value = "/mastermind/new", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<MastermindState> newGame() {
        MastermindController mc = new MastermindController();
        Long gameId = random.nextLong();
        liveGames.put(gameId, mc);

        MastermindState state = new MastermindState(gameId);

        return assembler.toResource( state );
    }

    @GetMapping(value = "/mastermind/{id}/guess/{guess}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Resource<MastermindState> makeGuess(@PathVariable("id") Long gameId, @PathVariable("guess") String guess) {
        MastermindController mc = liveGames.get(gameId);

        if (mc==null) {
            throw new GameNotFoundException("Game not found: "+gameId);
        }

        MastermindState state = new MastermindState(gameId);

        List<MastermindColour> attempt = getAttempt(guess);

        List<MastermindHint> hint =  mc.addAttempt(attempt);

        state.setHint(hint);

        if(mc.isFinished()) {
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

    private void cleanUp(Long gameId) {
        liveGames.remove(gameId);
    }

    private List<MastermindColour> getAttempt(String guess) {
        List<MastermindColour> attempt = new ArrayList<>();

        if (guess.length()>4) {
            guess = guess.substring(0,4);
        }

        for(int i=0; i!=guess.length(); i++) {
            switch (guess.charAt(i)) {
                case 'B':
                case 'b':
                    attempt.add(MastermindColour.BLUE);
                    break;
                case 'G':
                case 'g':
                    attempt.add(MastermindColour.GREEN);
                    break;
                case 'O':
                case 'o':
                    attempt.add(MastermindColour.ORANGE);
                    break;
                case 'P':
                case 'p':
                    attempt.add(MastermindColour.PURPLE);
                    break;
                case 'R':
                case 'r':
                    attempt.add(MastermindColour.RED);
                    break;
                case 'Y':
                case 'y':
                    attempt.add(MastermindColour.YELLOW);
                    break;
                default:
//                        attempt.add(MastermindColour.DUMMY);
            }
        }
        return attempt;
    }

    /*
    public Resource<SlideState> newGame() {
        spc.scramble();

        SlideState state = new SlideState(id, spc.getBoard());

        state.setMoves(spc.getValidMoves());

        return assembler.toResource(state);
    }

     */
}
