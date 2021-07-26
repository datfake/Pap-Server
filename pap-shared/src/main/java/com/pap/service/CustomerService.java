package com.pap.service;

import com.pap.config.Constants;
import com.pap.domain.Customer;
import com.pap.repository.CustomerRepository;
import com.pap.security.SecurityUtils;
import com.pap.service.dto.CustomerDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    private final CacheManager cacheManager;

    public CustomerService(CustomerRepository customerRepository, CacheManager cacheManager) {
        this.customerRepository = customerRepository;
        this.cacheManager = cacheManager;
    }

    public Optional<Customer> requestPasswordReset(String mail) {
        return customerRepository.findOneByEmailIgnoreCase(mail)
            .filter(Customer::isActivated)
            .map(user -> {
                // chua quyet dinh
                this.clearUserCaches(user);
                return user;
            });
    }

    public Customer registerCustomer(String numberPhone) {
        Customer newCustomer = new Customer();
        newCustomer.setPhone(numberPhone);
        newCustomer.setActivated(true);
        newCustomer.setCreatedBy("mine");
        newCustomer.setLastModifiedBy("mine");
        customerRepository.save(newCustomer);
        // this.clearUserCaches(newCustomer);
        log.debug("Tạo mới người dùng: {}", newCustomer);
        return newCustomer;
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param customerDTO user to update.
     * @return updated user.
     */
    public Optional<CustomerDTO> updateUser(CustomerDTO customerDTO) {
        return Optional.of(customerRepository
            .findById(customerDTO.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(user -> {
                this.clearUserCaches(user);
                user.setPhone(customerDTO.getPhone());
                user.setFullName(customerDTO.getFullName());
                if (customerDTO.getEmail() != null) {
                    user.setEmail(customerDTO.getEmail().toLowerCase());
                }
                user.setImageUrl(customerDTO.getImageUrl());
                user.setActivated(customerDTO.isActivated());
                this.clearUserCaches(user);
                log.debug("Changed Information for Customer: {}", user);
                return user;
            })
            .map(CustomerDTO::new);
    }

    public void deleteUser(String login) {
        customerRepository.findOneByPhone(login).ifPresent(user -> {
            customerRepository.delete(user);
            this.clearUserCaches(user);
            log.debug("Deleted Customer: {}", user);
        });
    }

    public void updateUser(String fullName, String email, String imageUrl) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(customerRepository::findOneByPhone)
            .ifPresent(user -> {
                user.setFullName(fullName);
                if (email != null) {
                    user.setEmail(email.toLowerCase());
                }
                user.setImageUrl(imageUrl);
                this.clearUserCaches(user);
                log.debug("Changed Information for Customer: {}", user);
            });
    }

    @Transactional(readOnly = true)
    public Page<CustomerDTO> getAllManagedUsers(Pageable pageable) {
        return customerRepository.findAllByPhoneNot(pageable, Constants.ANONYMOUS_USER).map(CustomerDTO::new);
    }


    private void clearUserCaches(Customer customer) {
        Objects.requireNonNull(cacheManager.getCache(CustomerRepository.USERS_BY_PHONE_CACHE)).evict(customer.getPhone());
        if (customer.getEmail() != null) {
            Objects.requireNonNull(cacheManager.getCache(CustomerRepository.USERS_BY_EMAIL_CACHE)).evict(customer.getEmail());
        }
    }
}
