package com.perhammer.joshua.restgames;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class MastermindStateResourceAssembler implements ResourceAssembler<MastermindState, Resource<MastermindState>> {


    @Override
    public Resource<MastermindState> toResource(MastermindState mastermindState) {
        Integer gameId = Integer.parseInt(mastermindState.getGameId());

        Resource<MastermindState> resource = new Resource(mastermindState);

        if (mastermindState.isFinished()) {
            resource.add(
                    linkTo(methodOn(Mastermind.class).newGame()).withRel("new")
            );
        } else {
            resource.add(
                linkTo(methodOn(Mastermind.class).makeGuess(gameId, "")).withRel("guess")
            );
            resource.add(
                    linkTo(methodOn(Mastermind.class).newGame(gameId)).withRel("new")
            );
        }

        return resource;
    }
}
