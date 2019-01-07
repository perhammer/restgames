package com.perhammer.joshua.registration;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class RegistrationController {

    private final RegistrationRepository repository;
    private final RegistrationResourceAssembler assembler;

    public RegistrationController(RegistrationRepository repository, RegistrationResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping(value = "/registrations", produces = {MediaType.APPLICATION_JSON_VALUE})
    Resources<Resource<Registration>> all() {
        List<Resource<Registration>> registrations = repository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(registrations,
                linkTo(methodOn(RegistrationController.class).all()).withSelfRel());

    }

    @GetMapping(value = "/registrations/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    Resource<Registration> one(@PathVariable Long id) {
        Registration registration = repository.findById(id).orElseThrow(()-> new RegistrationNotFoundException(id));

        return assembler.toResource(registration);
    }

    @PostMapping("/register")
    Resource<Registration> newEmployee(@RequestBody Registration newRegistration) {
        return assembler.toResource(repository.save(newRegistration));
    }

}
