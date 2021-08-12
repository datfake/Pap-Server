package com.pap.web.rest;

import com.pap.domain.Courier;
import com.pap.exception.InvalidPasswordException;
import com.pap.repository.CourierRepository;
import com.pap.service.CourierService;
import com.pap.service.MailService;
import com.pap.service.SmsService;
import com.pap.service.dto.CourierVM;
import com.pap.service.dto.PasswordChangeDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /*@PutMapping("/account")
    public ResponseEntity updateAccount(@Valid @RequestBody CourierDTO courierDTO) {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current courier login not found"));
        Optional<Courier> courier = courierRepository.findOneByPhone(userLogin);
        if (!courier.isPresent()) {
            throw new AccountResourceException("Courier could not be found");
        }
        courierService.updateUser(courierDTO.getEmail(), courierDTO.getAvatar());
        return new ResponseEntity("Thay đổi thông tin thành công", HttpStatus.OK);
    }*/

    @PostMapping(path = "/account/change-password")
    public ResponseEntity changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
        if (!checkPasswordLength(passwordChangeDto.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        courierService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
        return new ResponseEntity("Thay đổi mật khẩu thành công", HttpStatus.OK);
    }

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
            password.length() >= CourierVM.PASSWORD_MIN_LENGTH &&
            password.length() <= CourierVM.PASSWORD_MAX_LENGTH;
    }
}
