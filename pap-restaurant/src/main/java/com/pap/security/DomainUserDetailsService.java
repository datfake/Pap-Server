package com.pap.security;

import com.pap.domain.ManagerRestaurant;
import com.pap.repository.ManagerRestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final ManagerRestaurantRepository managerRestaurantRepository;

    public DomainUserDetailsService(ManagerRestaurantRepository managerRestaurantRepository) {
        this.managerRestaurantRepository = managerRestaurantRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String email) {
        log.debug("Authenticating {}", email);

        return managerRestaurantRepository.findOneByEmailIgnoreCase(email)
            .map(user -> createSpringSecurityUser(email, user))
            .orElseThrow(() -> new UsernameNotFoundException("ManagerRestaurant with email " + email + " was not found in the database"));
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String email, ManagerRestaurant managerRestaurant) {
        if (!managerRestaurant.isActivated()) {
            throw new UserNotActivatedException("ManagerRestaurant " + email + " was not activated");
        }
        return new org.springframework.security.core.userdetails.User(managerRestaurant.getEmail(),
            managerRestaurant.getPassword(),
            AuthorityUtils.createAuthorityList("ROLE_USER"));
    }
}
