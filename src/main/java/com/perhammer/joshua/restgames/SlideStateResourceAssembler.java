package com.perhammer.joshua.restgames;

import com.perhammer.joshua.slidingpuzzle.SlidingMove;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class SlideStateResourceAssembler implements ResourceAssembler<SlideState, Resource<SlideState>> {

    @Override
    public Resource<SlideState> toResource(SlideState slideState) {

        Long gameId = Long.parseLong(slideState.getGameId());

        Resource<SlideState> resource = new Resource(slideState);

        if(slideState.getMoves().size()>0) {
            resource.add(
                    linkTo(methodOn(Slide.class).newGame(gameId)).withRel("new")
            );
            for(SlidingMove move:slideState.getMoves()) {
                Link link;
                switch(move) {
                    case NORTH:
                        link = linkTo(methodOn(Slide.class).up(gameId)).withRel(move+"");
                        break;
                    case SOUTH:
                        link = linkTo(methodOn(Slide.class).down(gameId)).withRel(move+"");
                        break;
                    case EAST:
                        link = linkTo(methodOn(Slide.class).right(gameId)).withRel(move+"");
                        break;
                    case WEST:
                        link = linkTo(methodOn(Slide.class).left(gameId)).withRel(move+"");
                        break;
                    default:
                        link = null;
                }
                if (null!=link) {
                    resource.add(link);
                }
            }
        } else {
            resource.add(
                    linkTo(methodOn(Slide.class).newGame()).withRel("new")
            );
        }

        return resource;
    }
}
