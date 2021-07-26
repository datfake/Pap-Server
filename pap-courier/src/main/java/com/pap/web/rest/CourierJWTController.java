package com.pap.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pap.domain.Courier;
import com.pap.exception.BadRequestAlertException;
import com.pap.repository.CourierRepository;
import com.pap.security.jwt.JWTConfigurer;
import com.pap.security.jwt.TokenProvider;
import com.pap.web.rest.vm.LoginVM;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;

/**
 * Controller to authenticate courier.
 */
@RestController
@RequestMapping("/api")
public class CourierJWTController {

    private final TokenProvider tokenProvider;

    private final CourierRepository courierRepository;

    private final AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CourierJWTController(TokenProvider tokenProvider, CourierRepository courierRepository, AuthenticationManagerBuilder authenticationManagerBuilder, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.courierRepository = courierRepository;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/authenticate")
    public ResponseEntity authorize(@Valid @RequestBody LoginVM loginVM, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getPhone(), loginVM.getPassword());
        try {
            Optional<Courier> courier = courierRepository.findOneByPhone(loginVM.getPhone());
            if (courier.isPresent()) {
                if (passwordEncoder.matches(loginVM.getPassword(), courier.get().getPassword())) {
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
