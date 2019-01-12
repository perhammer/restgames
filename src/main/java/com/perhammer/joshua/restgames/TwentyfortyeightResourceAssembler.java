package com.perhammer.joshua.restgames;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class TwentyfortyeightResourceAssembler implements ResourceAssembler<TwentyfortyeightState, Resource<TwentyfortyeightState>> {
    @Override
    public Resource<TwentyfortyeightState> toResource(TwentyfortyeightState twentyfortyeightState) {
        Integer gameId = Integer.parseInt(twentyfortyeightState.getGameId());

        Resource<TwentyfortyeightState> resource = new Resource(twentyfortyeightState);

        if (!twentyfortyeightState.isFinished()) {
            resource.add(
                    linkTo(methodOn(Twentyfortyeight.class).newGame(gameId)).withRel("new")
            );
            resource.add(
                    linkTo(methodOn(Twentyfortyeight.class).slideUp(gameId)).withRel("up")
            );
            resource.add(
                    linkTo(methodOn(Twentyfortyeight.class).slideDown(gameId)).withRel("down")
            );
            resource.add(
                    linkTo(methodOn(Twentyfortyeight.class).slideLeft(gameId)).withRel("left")
            );
            resource.add(
                    linkTo(methodOn(Twentyfortyeight.class).slideRight(gameId)).withRel("right")
            );
        } else {
            resource.add(
                    linkTo(methodOn(Twentyfortyeight.class).newGame()).withRel("new")
            );
        }

        return resource;
    }
}
