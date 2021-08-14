package com.pap.web.rest;

import com.pap.domain.Customer;
import com.pap.exception.EmailAlreadyUsedException;
import com.pap.repository.CustomerRepository;
import com.pap.security.SecurityUtils;
import com.pap.service.CustomerService;
import com.pap.service.MailService;
import com.pap.service.dto.CustomerDTO;
import com.pap.service.dto.RestaurantDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class CustomerController {

    private static class AccountResourceException extends RuntimeException {
        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerRepository customerRepository;

    private final CustomerService customerService;

    public CustomerController(CustomerRepository customerRepository, CustomerService customerService, MailService mailService, CustomerRepository customerRepository1) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    @PutMapping("/account")
    public ResponseEntity<CustomerDTO> updateAccount(@Valid @RequestBody CustomerDTO customerDTO) {
        String numberPhoneLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Người dùng hiện tại không tìm thấy"));
        Optional<Customer> customer = customerRepository.findOneByPhone(numberPhoneLogin);
        if (!customer.isPresent() ) {
            throw new AccountResourceException("Người dùng không tồn tại");
        }
        customerService.updateUser(customerDTO.getFullName(), customerDTO.getEmail(), customerDTO.getAvatar());
        return new ResponseEntity("Thay đổi thông tin thành công", HttpStatus.OK);
    }

    @GetMapping("/restaurant/{categoryId}")
    public List<RestaurantDTO> getAllRestaurantByCategoryId(@PathVariable Integer categoryId) {
        log.debug("REST request get all restaurant by categoryId");
        return null;
    }
}
