package com.perhammer.joshua.registration;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class RegistrationsService implements UserDetailsService {

    private final RegistrationRepository repository;

    public RegistrationsService(RegistrationRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String teamname) throws UsernameNotFoundException {
        Registration registration = repository.findByTeamname(teamname);
        if (registration == null) {
            throw new UsernameNotFoundException(teamname);
        }
        return new User(registration.getTeamname(), registration.getTeampass(), emptyList());
    }
}
