package com.pap.security;

import com.pap.domain.Courier;
import com.pap.repository.CourierRepository;
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
public class DomainCourierDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainCourierDetailsService.class);

    private final CourierRepository courierRepository;

    public DomainCourierDetailsService(CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String phone) {
        log.debug("Authenticating {}", phone);

        return courierRepository.findOneByPhone(phone)
            .map(user -> createSpringSecurityUser(phone, user))
            .orElseThrow(() -> new UsernameNotFoundException("Courier with email " + phone + " was not found in the database"));

    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, Courier courier) {
        if (!courier.isActivated()) {
            throw new UserNotActivatedException("Courier " + lowercaseLogin + " was not activated");
        }
        return new org.springframework.security.core.userdetails.User(courier.getPhone(), courier.getPassword(),
            AuthorityUtils.createAuthorityList("ROLE_USER"));
    }
}
