package com.perhammer.joshua.registration;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class RegistrationResourceAssembler implements ResourceAssembler<Registration, Resource<Registration>> {

    private static final String PASSWORD_OBSCURED_FOR_DISPLAY = "**********";

    @Override
    public Resource<Registration> toResource(Registration registration) {
        registration.setTeampass(PASSWORD_OBSCURED_FOR_DISPLAY);

        Link tmp = linkTo(methodOn(RegistrationController.class).all()).withRel("registrations");
        String base = tmp.getHref();
        base = base.substring(0, base.indexOf("registrations"))+"login";

        return new Resource<>(registration,
                linkTo(methodOn(RegistrationController.class).one(registration.getId())).withSelfRel(),
                linkTo(methodOn(RegistrationController.class).all()).withRel("registrations"),
                new Link(base, "login"));
    }
}
