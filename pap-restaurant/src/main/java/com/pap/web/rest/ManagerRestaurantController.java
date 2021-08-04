package com.pap.web.rest;

import com.pap.domain.ManagerRestaurant;
import com.pap.exception.EmailAlreadyUsedException;
import com.pap.exception.InvalidPasswordException;
import com.pap.exception.NumberPhoneAlreadyUsedException;
import com.pap.repository.ManagerRestaurantRepository;
import com.pap.security.SecurityUtils;
import com.pap.service.MailService;
import com.pap.service.ManagerRestaurantService;
import com.pap.service.SmsService;
import com.pap.service.dto.ManagerRestaurantDTO;
import com.pap.service.dto.PasswordChangeDTO;
import com.pap.web.rest.vm.ManagedManagerRestaurantVM;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

/**
 * REST controller for managing the current manager restaurant's account.
 */
@RestController
@RequestMapping("/api")
public class ManagerRestaurantController {

    private static class AccountResourceException extends RuntimeException {
        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(ManagerRestaurantController.class);

    private final ManagerRestaurantRepository managerRestaurantRepository;

    private final ManagerRestaurantService managerRestaurantService;

    private final SmsService smsService;

    public ManagerRestaurantController(ManagerRestaurantRepository managerRestaurantRepository, ManagerRestaurantService managerRestaurantService, MailService mailService, SmsService smsService) {
        this.managerRestaurantRepository = managerRestaurantRepository;
        this.managerRestaurantService = managerRestaurantService;
        this.smsService = smsService;
    }

    /**
     * {@code POST  /register} : register the ManagerRestaurant.
     *
     * @param managedUserVM the managed user View Model.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws NumberPhoneAlreadyUsedException {@code 400 (Bad Request)} if the login is already used.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity registerAccount(@Valid @RequestBody ManagedManagerRestaurantVM managedUserVM) {
        if (!checkPasswordLength(managedUserVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        managerRestaurantService.registerUser(managedUserVM, managedUserVM.getPassword());
        // mailService.sendActivationEmail(managerRestaurant);
        // this.smsService.sendSms(new SmsRequest(managedUserVM.getPhone(), "Ban da dang ky thanh cong tai khoan chu quan cua pap. Chuc ban co nhung trai nghiem tuyet voi."));
        return new ResponseEntity("Đăng ký tài khoản thành công", HttpStatus.CREATED);
    }

    /**
     * {@code GET  /authenticate} : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request.
     * @return the login if the user is authenticated.
     */
    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }


    /**
     * {@code POST  /account} : update the current user information.
     *
     * @param managerRestaurantDTO the current user information.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user login wasn't found.
     */
    @PutMapping("/account")
    public ResponseEntity updateAccount(@Valid @RequestBody ManagerRestaurantDTO managerRestaurantDTO) {
        String managerRestaurantPhone = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current manager restaurant login not found"));
        Optional<ManagerRestaurant> managerRestaurant = managerRestaurantRepository.findOneByPhone(managerRestaurantPhone);
        if (!managerRestaurant.isPresent()) {
            throw new AccountResourceException("ManagerRestaurant could not be found");
        }
        managerRestaurantService.updateUser(managerRestaurantDTO.getNameRestaurant(), managerRestaurantDTO.getSummary(), managerRestaurantDTO.getContent(), managerRestaurantDTO.getAddress(), managerRestaurantDTO.getEmail(), managerRestaurantDTO.getAvatar());
        return new ResponseEntity("Thay đổi thông tin thành công", HttpStatus.OK);
    }

    /**
     * {@code POST  /account/change-password} : changes the current user's password.
     *
     * @param passwordChangeDto current and new password.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the new password is incorrect.
     */
    @PostMapping(path = "/account/change-password")
    public ResponseEntity changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
        if (!checkPasswordLength(passwordChangeDto.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        managerRestaurantService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
        return new ResponseEntity("Thay đổi mật khẩu thành công", HttpStatus.OK);
    }

    /**
     * {@code POST   /account/reset-password/init} : Send an email to reset the password of the user.
     *
     * @param phone the phone of the user.
     */
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

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= ManagedManagerRestaurantVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedManagerRestaurantVM.PASSWORD_MAX_LENGTH;
    }
}
