package com.pap.web.rest;

import com.pap.domain.Courier;
import com.pap.exception.EmailAlreadyUsedException;
import com.pap.exception.InvalidPasswordException;
import com.pap.exception.NumberPhoneAlreadyUsedException;
import com.pap.repository.CourierRepository;
import com.pap.security.SecurityUtils;
import com.pap.service.CourierService;
import com.pap.service.MailService;
import com.pap.service.SmsService;
import com.pap.service.dto.CourierDTO;
import com.pap.service.dto.PasswordChangeDTO;
import com.pap.web.rest.vm.ManagedUserVM;
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
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class CourierController {

    private static class AccountResourceException extends RuntimeException {
        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(CourierController.class);

    private final CourierRepository courierRepository;

    private final CourierService courierService;

    private final SmsService smsService;

    public CourierController(CourierRepository courierRepository, CourierService courierService, MailService mailService, SmsService smsService) {

        this.courierRepository = courierRepository;
        this.courierService = courierService;
        this.smsService = smsService;
    }

    /**
     * {@code POST  /register} : register the user.
     *
     * @param managedUserVM the managed user View Model.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws NumberPhoneAlreadyUsedException {@code 400 (Bad Request)} if the login is already used.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
        if (!checkPasswordLength(managedUserVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        courierService.registerUser(managedUserVM, managedUserVM.getPassword());
        // mailService.sendActivationEmail(courier);
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
     * @param courierDTO the current user information.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user login wasn't found.
     */
    @PutMapping("/account")
    public ResponseEntity updateAccount(@Valid @RequestBody CourierDTO courierDTO) {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current courier login not found"));
        Optional<Courier> courier = courierRepository.findOneByPhone(userLogin);
        if (!courier.isPresent()) {
            throw new AccountResourceException("Courier could not be found");
        }
        courierService.updateUser(courierDTO.getEmail(), courierDTO.getAvatar());
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
        courierService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
        return new ResponseEntity("Thay đổi mật khẩu thành công", HttpStatus.OK);
    }

    /**
     * {@code POST   /account/reset-password/init} : Send an email to reset the password of the user.
     *
     * @param mail the mail of the user.
     */
    @PostMapping(path = "/account/reset-password/init")
    public void requestPasswordReset(@RequestBody String mail) {
        Optional<Courier> user = courierService.requestPasswordReset(mail);
        if (user.isPresent()) {
            // mailService.sendPasswordResetMail(user.get());
        } else {
            // Pretend the request has been successful to prevent checking which emails really exist
            // but log that an invalid attempt has been made
            log.warn("Password reset requested for non existing mail");
        }
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
    }
}
