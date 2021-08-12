package com.pap.web.rest;

import com.pap.domain.ManagerRestaurant;
import com.pap.exception.BadRequestAlertException;
import com.pap.repository.ManagerRestaurantRepository;
import com.pap.security.jwt.JWTConfigurer;
import com.pap.security.jwt.TokenProvider;
import com.pap.web.rest.vm.LoginVM;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class ManagerRestaurantJWTController {

    private final TokenProvider tokenProvider;

    private final ManagerRestaurantRepository managerRestaurantRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ManagerRestaurantJWTController(TokenProvider tokenProvider, ManagerRestaurantRepository managerRestaurantRepository, AuthenticationManagerBuilder authenticationManagerBuilder, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.managerRestaurantRepository = managerRestaurantRepository;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/authenticate")
    public ResponseEntity authorize(@Valid @RequestBody LoginVM loginVM, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getEmail(), loginVM.getPassword());
        try {
            Optional<ManagerRestaurant> managerRestaurant = managerRestaurantRepository.findOneByEmailIgnoreCase(loginVM.getEmail());
            if (managerRestaurant.isPresent()) {
                if (passwordEncoder.matches(loginVM.getPassword(), managerRestaurant.get().getPassword())) {
                    Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    String jwt = tokenProvider.createToken(authentication);
                    response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
                    return ResponseEntity.ok(new JWTToken(jwt));
                } else {
                    throw new BadRequestAlertException("Mật khẩu không đúng", "PasswordErr", " password not existed");
                }
            } else {
                throw new BadRequestAlertException("Tên đăng nhập hoặc mật khẩu không đúng", "AccountOrPasswordErr", "not existed");
            }
        }
        catch (AuthenticationException e) {
            e.printStackTrace();
            return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",
                 "Login fail"), HttpStatus.UNAUTHORIZED);
        }
    }
    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
