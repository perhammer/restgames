package com.perhammer.joshua.restgames;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class MinesweeperStateResourceAssembler implements ResourceAssembler<MinesweeperState, Resource<MinesweeperState>> {

    @Override
    public Resource<MinesweeperState> toResource(MinesweeperState minesweeperState) {
        Long gameId = Long.parseLong(minesweeperState.getGameId());

        Resource<MinesweeperState> resource = new Resource<>(minesweeperState);

        if(minesweeperState.isFinished()) {
            resource.add(
                    linkTo(methodOn(Minesweeper.class).newGame()).withRel("new")
            );
        } else {
            resource.add(
                    linkTo(methodOn(Minesweeper.class).reveal(gameId, 0, 0)).withRel("reveal")
            );
            resource.add(
                    linkTo(methodOn(Minesweeper.class).mark(gameId, 0, 0)).withRel("mark")
            );
            resource.add(
                    linkTo(methodOn(Minesweeper.class).unmark(gameId, 0, 0)).withRel("unmark")
            );
            resource.add(
                    linkTo(methodOn(Minesweeper.class).newGame(gameId)).withRel("new")
            );
        }

        return resource;
    }

}
