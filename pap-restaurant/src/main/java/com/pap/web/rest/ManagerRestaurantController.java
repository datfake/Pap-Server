package com.pap.web.rest;

import com.pap.domain.Discount;
import com.pap.domain.ManagerRestaurant;
import com.pap.exception.BadRequestAlertException;
import com.pap.exception.InvalidPasswordException;
import com.pap.repository.ManagerRestaurantRepository;
import com.pap.service.DiscountService;
import com.pap.service.MailService;
import com.pap.service.ManagerRestaurantService;
import com.pap.service.SmsService;
import com.pap.service.dto.DiscountDTO;
import com.pap.service.dto.ManagerRestaurantVM;
import com.pap.service.dto.PasswordChangeDTO;
import io.github.jhipster.web.util.HeaderUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * REST controller for managing the current manager restaurant's account.
 */
@RestController
@RequestMapping("/api")
public class ManagerRestaurantController {

    @Value("${pap.clientApp.name}")
    private String applicationName;

    private static class AccountResourceException extends RuntimeException {
        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(ManagerRestaurantController.class);

    private final ManagerRestaurantRepository managerRestaurantRepository;

    private final ManagerRestaurantService managerRestaurantService;

    private final DiscountService discountService;

    private final SmsService smsService;

    public ManagerRestaurantController(ManagerRestaurantRepository managerRestaurantRepository, ManagerRestaurantService managerRestaurantService, MailService mailService, DiscountService discountService, SmsService smsService) {
        this.managerRestaurantRepository = managerRestaurantRepository;
        this.managerRestaurantService = managerRestaurantService;
        this.discountService = discountService;
        this.smsService = smsService;
    }

    /*@PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity registerAccount(@Valid @RequestBody ManagerRestaurantVM managedUserVM) {
        if (!checkPasswordLength(managedUserVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        managerRestaurantService.registerUser(managedUserVM, managedUserVM.getPassword());
        // mailService.sendActivationEmail(managerRestaurant);
        // this.smsService.sendSms(new SmsRequest(managedUserVM.getPhone(), "Ban da dang ky thanh cong tai khoan chu quan cua pap. Chuc ban co nhung trai nghiem tuyet voi."));
        return new ResponseEntity("Đăng ký tài khoản thành công", HttpStatus.CREATED);
    }*/

    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /*@PutMapping("/account")
    public ResponseEntity updateAccount(@Valid @RequestBody ManagerRestaurantDTO managerRestaurantDTO) {
        String managerRestaurantPhone = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current manager restaurant login not found"));
        Optional<ManagerRestaurant> managerRestaurant = managerRestaurantRepository.findOneByPhone(managerRestaurantPhone);
        if (!managerRestaurant.isPresent()) {
            throw new AccountResourceException("ManagerRestaurant could not be found");
        }
        managerRestaurantService.updateManagerRestaurant(managerRestaurantDTO.getNameRestaurant(), managerRestaurantDTO.getSummary(), managerRestaurantDTO.getContent(), managerRestaurantDTO.getAddress(), managerRestaurantDTO.getEmail(), managerRestaurantDTO.getAvatar());
        return new ResponseEntity("Thay đổi thông tin thành công", HttpStatus.OK);
    }*/

    @PostMapping(path = "/account/change-password")
    public ResponseEntity changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
        if (!checkPasswordLength(passwordChangeDto.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        managerRestaurantService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
        return new ResponseEntity("Thay đổi mật khẩu thành công", HttpStatus.OK);
    }

    @PostMapping(path = "/account/reset-password/init")
    public void requestPasswordReset(@RequestBody String phone) {
        Optional<ManagerRestaurant> managerRestaurant = managerRestaurantService.requestPasswordReset(phone);
        if (managerRestaurant.isPresent()) {
            // not thing
        } else {
            // Pretend the request has been successful to prevent checking which emails really exist
            // but log that an invalid attempt has been made
            log.warn("Password reset requested for non existing mail");
        }
    }

    @PostMapping(path = "/discount")
    public ResponseEntity<Discount> createDiscount(@RequestBody DiscountDTO discountDTO) throws URISyntaxException {
        if (discountDTO.getId() != null) {
            throw new BadRequestAlertException("A new Discount cannot already have an ID", "discount", "idexists");
        }
        Discount discount = discountService.createDiscount(discountDTO);
        return ResponseEntity.created(new URI("/api/discount/" + discount.getCode()))
            .headers(HeaderUtil.createAlert(applicationName,  "A courier is created with identifier " + discount.getCode(), discount.getCode()))
            .body(discount);
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= ManagerRestaurantVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagerRestaurantVM.PASSWORD_MAX_LENGTH;
    }
}
