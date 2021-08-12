package com.pap.web.rest;

import com.pap.domain.Courier;
import com.pap.domain.ManagerRestaurant;
import com.pap.exception.BadRequestAlertException;
import com.pap.exception.EmailAlreadyUsedException;
import com.pap.exception.NumberPhoneAlreadyUsedException;
import com.pap.repository.CourierRepository;
import com.pap.repository.ManagerRestaurantRepository;
import com.pap.security.AuthoritiesConstants;
import com.pap.service.CourierService;
import com.pap.service.MailService;
import com.pap.service.ManagerRestaurantService;
import com.pap.service.dto.CourierDTO;
import com.pap.service.dto.CourierVM;
import com.pap.service.dto.ManagerRestaurantDTO;
import com.pap.service.dto.ManagerRestaurantVM;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserResource {
    private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(Arrays.asList("id", "login", "firstName", "lastName", "email", "activated", "langKey"));

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    @Value("${pap.clientApp.name}")
    private String applicationName;

    private final ManagerRestaurantService managerRestaurantService;

    private final ManagerRestaurantRepository managerRestaurantRepository;

    private final CourierService courierService;

    private final CourierRepository courierRepository;

    private final MailService mailService;

    public UserResource(ManagerRestaurantService managerRestaurantService, ManagerRestaurantRepository managerRestaurantRepository, CourierService courierService, CourierRepository courierRepository, MailService mailService) {
        this.managerRestaurantService = managerRestaurantService;
        this.managerRestaurantRepository = managerRestaurantRepository;
        this.courierService = courierService;
        this.courierRepository = courierRepository;
        this.mailService = mailService;
    }

    @PostMapping("/account-restaurant")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ManagerRestaurant> createAccountRestaurant(@Valid @RequestBody ManagerRestaurantVM managerRestaurantVM) throws URISyntaxException {
        log.debug("REST request to save ManagerRestaurant : {}", managerRestaurantVM);

        if (managerRestaurantVM.getId() != null) {
            throw new BadRequestAlertException("A new ManagerRestaurant cannot already have an ID", "userManagement", "idexists");
        } else if (managerRestaurantRepository.findOneByPhone(managerRestaurantVM.getPhone()).isPresent()) {
            throw new NumberPhoneAlreadyUsedException();
        } else if (managerRestaurantRepository.findOneByEmailIgnoreCase(managerRestaurantVM.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {
            ManagerRestaurant managerRestaurant = managerRestaurantService.createManagerRestaurant(managerRestaurantVM);
            mailService.sendCreationEmail(managerRestaurant);
            return ResponseEntity.created(new URI("/api/users/" + managerRestaurant.getPhone()))
                .headers(HeaderUtil.createAlert(applicationName,  "A AccountRestaurant is created with identifier " + managerRestaurant.getPhone(), managerRestaurant.getPhone()))
                .body(managerRestaurant);
        }
    }

    @PutMapping("/account-restaurant")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ManagerRestaurantDTO> updateAccountRestaurant(@Valid @RequestBody ManagerRestaurantDTO managerRestaurantDTO) {
        log.debug("REST request to update AccountRestaurant : {}", managerRestaurantDTO);
        Optional<ManagerRestaurant> existingManagerRestaurant= managerRestaurantRepository.findOneByEmailIgnoreCase(managerRestaurantDTO.getEmail());
        if (existingManagerRestaurant.isPresent() && (!existingManagerRestaurant.get().getId().equals(managerRestaurantDTO.getId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingManagerRestaurant = managerRestaurantRepository.findOneByPhone(managerRestaurantDTO.getPhone().toLowerCase());
        if (existingManagerRestaurant.isPresent() && (!existingManagerRestaurant.get().getId().equals(managerRestaurantDTO.getId()))) {
            throw new NumberPhoneAlreadyUsedException();
        }
        Optional<ManagerRestaurantDTO> updatedManagerRestaurant = managerRestaurantService.updateManagerRestaurant(managerRestaurantDTO);

        return ResponseUtil.wrapOrNotFound(updatedManagerRestaurant,
            HeaderUtil.createAlert(applicationName, "A AccountRestaurant is updated with identifier " + managerRestaurantDTO.getPhone(), managerRestaurantDTO.getPhone()));
    }

    @GetMapping("/account-restaurants")
    public ResponseEntity<List<ManagerRestaurantDTO>> getAllManagerRestaurants(Pageable pageable) {
        if (!onlyContainsAllowedProperties(pageable)) {
            return ResponseEntity.badRequest().build();
        }

        final Page<ManagerRestaurantDTO> page = managerRestaurantService.getAllManagedUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/account-restaurant/{phone}")
    public ResponseEntity<ManagerRestaurantDTO> getAccountRestaurant(@PathVariable String phone) {
        log.debug("REST request to get AccountRestaurant : {}", phone);
        return ResponseUtil.wrapOrNotFound(
            managerRestaurantService.getAccountRestaurantByPhone(phone)
                .map(ManagerRestaurantDTO::new));
    }

    @DeleteMapping("/account-restaurant/{phone}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteAccountRestaurant(@PathVariable String phone) {
        log.debug("REST request to delete AccountRestaurant: {}", phone);
        managerRestaurantService.deleteManagerRestaurant(phone);
        return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName,  "A user is deleted with identifier " + phone, phone)).build();
    }

    @PostMapping("/account-courier")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Courier> createAccountCourier(@Valid @RequestBody CourierVM courierVM) throws URISyntaxException {
        log.debug("REST request to save courier : {}", courierVM);

        if (courierVM.getId() != null) {
            throw new BadRequestAlertException("A new Courier cannot already have an ID", "Courier", "idexists");
        } else if (courierRepository.findOneByPhone(courierVM.getPhone()).isPresent()) {
            throw new NumberPhoneAlreadyUsedException();
        } else if (courierRepository.findOneByEmailIgnoreCase(courierVM.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {
            Courier courier = courierService.createCourier(courierVM);
            mailService.sendCreationEmail(courier);
            return ResponseEntity.created(new URI("/api/users/" + courier.getPhone()))
                .headers(HeaderUtil.createAlert(applicationName,  "A courier is created with identifier " + courier.getPhone(), courier.getPhone()))
                .body(courier);
        }
    }

    @PutMapping("/account-courier")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<CourierDTO> updateAccountCourier(@Valid @RequestBody CourierDTO courierDTO) {
        log.debug("REST request to update Courier : {}", courierDTO);
        Optional<Courier> existingCourier = courierRepository.findOneByEmailIgnoreCase(courierDTO.getEmail());
        if (existingCourier.isPresent() && (!existingCourier.get().getId().equals(courierDTO.getId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingCourier = courierRepository.findOneByPhone(courierDTO.getPhone().toLowerCase());
        if (existingCourier.isPresent() && (!existingCourier.get().getId().equals(courierDTO.getId()))) {
            throw new NumberPhoneAlreadyUsedException();
        }
        Optional<CourierDTO> updatedManagerRestaurant = courierService.updateCourier(courierDTO);

        return ResponseUtil.wrapOrNotFound(updatedManagerRestaurant,
            HeaderUtil.createAlert(applicationName, "A courier is updated with identifier " + courierDTO.getPhone(), courierDTO.getPhone()));
    }

    @GetMapping("/account-couriers")
    public ResponseEntity<List<CourierDTO>> getAllCouriers(Pageable pageable) {
        if (!onlyContainsAllowedProperties(pageable)) {
            return ResponseEntity.badRequest().build();
        }
        final Page<CourierDTO> page = courierService.getAllManagedUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }

    @GetMapping("/account-courier/{phone}")
    public ResponseEntity<CourierDTO> getCourier(@PathVariable String phone) {
        log.debug("REST request to get Courier : {}", phone);
        return ResponseUtil.wrapOrNotFound(
            courierService.getCourierByPhone(phone)
                .map(CourierDTO::new));
    }

    @DeleteMapping("/account-courier/{phone}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteAccountCourier(@PathVariable String phone) {
        log.debug("REST request to delete AccountCourier: {}", phone);
        courierService.deleteAccountCourier(phone);
        return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName,  "A user is deleted with identifier " + phone, phone)).build();
    }
}
