package com.pap.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pap.domain.Customer;
import com.pap.repository.CustomerRepository;
import com.pap.security.jwt.JWTConfigurer;
import com.pap.security.jwt.TokenProvider;
import com.pap.service.CustomerService;
import com.pap.service.SmsService;
import com.pap.service.dto.OtpDTO;
import com.pap.service.dto.SmsRequest;
import com.pap.web.rest.vm.CustomerLogin;
import org.redisson.Redisson;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Controller to authenticate customer.
 */
@RestController
@RequestMapping("/api")
public class CustomerJWTController {

    private final TokenProvider tokenProvider;

    private final CustomerRepository customerRepository;

    private final CustomerService customerService;

    private final AuthenticationManager authenticationManager;

    private final SmsService smsService;

    private final RedissonClient client;

    public CustomerJWTController(TokenProvider tokenProvider, CustomerRepository customerRepository, CustomerService customerService, AuthenticationManager authenticationManager, SmsService smsService) {
        this.tokenProvider = tokenProvider;
        this.customerRepository = customerRepository;
        this.customerService = customerService;
        this.authenticationManager = authenticationManager;
        this.smsService = smsService;
        client = Redisson.create();
    }

    @PostMapping("/authenticate")
    public ResponseEntity authorize(@Valid @RequestBody CustomerLogin customerLogin, HttpServletResponse response) {
        RMapCache<String, Map<String, Integer>> mapOtp = client.getMapCache("otp-pap");
        Map<String, Integer> mapOtpAndCountFail = mapOtp.get(customerLogin.getPhone());
        int countFail = mapOtpAndCountFail.get("countFail");
        if(countFail >2 ) {
            return new ResponseEntity<>(Collections.singletonMap("AuthenticationException", "Nhập sai mã OTP quá số lần quy định"), HttpStatus.UNAUTHORIZED);
        }
        if(!mapOtpAndCountFail.get("otp").toString().equals(customerLogin.getOtp())) {
           mapOtpAndCountFail.put("countFail", ++ countFail);
           mapOtp.put(customerLogin.getPhone(), mapOtpAndCountFail);
           return new ResponseEntity<>(Collections.singletonMap("AuthenticationException", "Mã OTP không đúng"), HttpStatus.UNAUTHORIZED);
       }

        try {
            Optional<Customer> customer = customerRepository.findOneByPhone(customerLogin.getPhone());
            if (!customer.isPresent()) customerService.registerCustomer(customerLogin.getPhone());
            Authentication authentication = new UsernamePasswordAuthenticationToken(customerLogin.getPhone(), null, AuthorityUtils.createAuthorityList("ROLE_USER"));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.createToken(authentication);
            response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
            return ResponseEntity.ok(new JWTToken(jwt));
        }
        catch (AuthenticationException ae) {
            ae.printStackTrace();
            return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",
                ae.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/send-otp")
    public ResponseEntity sendOtp(@Valid @RequestBody OtpDTO otpDTO){
        try {
            RMapCache<String, Map<String, Integer>> mapOtp = client.getMapCache("otp-pap");
            Map<String, Integer> mapOtpAndCountFail = new HashMap<>();
            Integer otp = (int) (Math.random()*900000)+100000;
            mapOtpAndCountFail.put("otp", otp);
            mapOtpAndCountFail.put("countFail", 0);
            mapOtp.put(otpDTO.getPhone(), mapOtpAndCountFail, 3, TimeUnit.MINUTES);
            // this.smsService.sendSms(new SmsRequest(otpDTO.getPhone(), "Ma OTP xac thuc tai khoan PAP cua ban la:" + otp));
            return ResponseEntity.ok("Tạo OTP thành công: "+ otp);
            // return ResponseEntity.ok("Tạo OTP thành công.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(Collections.singletonMap("OTPException", "Tạo OTP thất bại"), HttpStatus.INTERNAL_SERVER_ERROR);
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
