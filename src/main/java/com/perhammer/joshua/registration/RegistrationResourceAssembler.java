package com.perhammer.joshua.registration;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class RegistrationResourceAssembler implements ResourceAssembler<Registration, Resource<Registration>> {

    @Override
    public Resource<Registration> toResource(Registration registration) {

        return new Resource<>(registration,
                linkTo(methodOn(RegistrationController.class).one(registration.getId())).withSelfRel(),
                linkTo(methodOn(RegistrationController.class).all()).withRel("registrations"));
    }
}
