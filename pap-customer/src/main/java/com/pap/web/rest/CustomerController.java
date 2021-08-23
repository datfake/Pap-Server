package com.pap.web.rest;

import com.pap.domain.CategoryItem;
import com.pap.domain.Customer;
import com.pap.repository.CustomerRepository;
import com.pap.security.SecurityUtils;
import com.pap.service.CategoryItemService;
import com.pap.service.CustomerService;
import com.pap.service.MailService;
import com.pap.service.ManagerRestaurantService;
import com.pap.service.dto.CustomerDTO;
import com.pap.service.dto.RestaurantDTO;
import io.github.jhipster.web.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    private final ManagerRestaurantService managerRestaurantService;

    private final CategoryItemService categoryItemService;

    public CustomerController(CustomerRepository customerRepository, CustomerService customerService, MailService mailService, CustomerRepository customerRepository1, ManagerRestaurantService managerRestaurantService, CategoryItemService categoryItemService) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
        this.managerRestaurantService = managerRestaurantService;
        this.categoryItemService = categoryItemService;
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
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurantByCategoryId(Pageable pageable, @PathVariable Integer categoryId) {
        log.debug("REST request get all restaurant by categoryId");
        final Page<RestaurantDTO> page = managerRestaurantService.getAllRestaurantsByCategoryId(pageable, categoryId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/item/{restaurantId}")
    public ResponseEntity<List<CategoryItem>> getAllItemByRestaurantId(Pageable pageable, @PathVariable String restaurantId) {
        log.debug("REST request get all item by restaurantId");
        final Page<CategoryItem> page = categoryItemService.getAllItemByRestaurantId(pageable, restaurantId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
