package com.pap.web.rest;

import com.pap.domain.*;
import com.pap.exception.BadRequestAlertException;
import com.pap.exception.InvalidPasswordException;
import com.pap.repository.ManagerRestaurantRepository;
import com.pap.security.SecurityUtils;
import com.pap.service.*;
import com.pap.service.dto.*;
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

    private final CategoryItemService categoryItemService;

    private final ItemService itemService;

    private final OptionItemService optionItemService;

    private final OptionItemChildService optionItemChildService;

    private final SmsService smsService;

    public ManagerRestaurantController(ManagerRestaurantRepository managerRestaurantRepository, ManagerRestaurantService managerRestaurantService, MailService mailService, DiscountService discountService, CategoryItemService categoryItemService, ItemService itemService, OptionItemService optionItemService, OptionItemChildService optionItemChildService, SmsService smsService) {
        this.managerRestaurantRepository = managerRestaurantRepository;
        this.managerRestaurantService = managerRestaurantService;
        this.discountService = discountService;
        this.categoryItemService = categoryItemService;
        this.itemService = itemService;
        this.optionItemService = optionItemService;
        this.optionItemChildService = optionItemChildService;
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
        String email = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Manager restaurant email not found"));
        discountDTO.setRestaurantEmail(email);
        if (discountDTO.getId() != null) {
            throw new BadRequestAlertException("A new Discount cannot already have an ID", "discount", "idexists");
        }
        Discount discount = discountService.createDiscount(discountDTO);
        return ResponseEntity.created(new URI("/api/discount/" + discount.getCode()))
            .headers(HeaderUtil.createAlert(applicationName,  "A courier is created with identifier " + discount.getId(), discount.getCode()))
            .body(discount);
    }

    @PostMapping(path = "/category-item")
    public ResponseEntity<CategoryItem> createCategoryItem(@RequestBody CategoryItemDTO categoryItemDTO) throws URISyntaxException {
        String email = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Manager restaurant email not found"));
        categoryItemDTO.setRestaurantEmail(email);
        if (categoryItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new CategoryItem cannot already have an ID", "discount", "idexists");
        }
        CategoryItem categoryItem = categoryItemService.createCategoryItem(categoryItemDTO);
        return ResponseEntity.created(new URI("/api/category-item"))
                .headers(HeaderUtil.createAlert(applicationName,  "A CategoryItem is created with identifier " + categoryItem.getId(), categoryItem.getName()))
                .body(categoryItem);
    }

    @PostMapping(path = "/item")
    public ResponseEntity<Object> createItem(@RequestBody ItemDTO itemDTO) throws URISyntaxException {
        String email = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Manager restaurant email not found"));
        if (!email.equalsIgnoreCase(itemDTO.getRestaurantEmail())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ban khong co quyen them noi dung nay!");
        }
        Item item = itemService.createItem(itemDTO);
        return ResponseEntity.created(new URI("/api/item"))
                .headers(HeaderUtil.createAlert(applicationName,  "A Item is created with identifier " + item.getId(), item.getName()))
                .body(item);
    }

    @PostMapping(path = "/option-item")
    public ResponseEntity<Object> createOptionItem(@RequestBody OptionItemDTO optionItemDTO) throws URISyntaxException {
        String email = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Manager restaurant email not found"));
        if (!email.equalsIgnoreCase(optionItemDTO.getRestaurantEmail())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ban khong co quyen them noi dung nay!");
        }
        OptionItem optionItem = optionItemService.createOptionItem(optionItemDTO);
        return ResponseEntity.created(new URI("/api/option-item"))
                .headers(HeaderUtil.createAlert(applicationName,  "A optionItem is created with identifier " + optionItem.getId(), optionItem.getName()))
                .body(optionItem);
    }

    @PostMapping(path = "/option-item-child")
    public ResponseEntity<Object> createOptionItemChild(@RequestBody OptionItemChildDTO optionItemChildDTO) throws URISyntaxException {
        String email = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Manager restaurant email not found"));
        if (!email.equalsIgnoreCase(optionItemChildDTO.getRestaurantEmail())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ban khong co quyen them noi dung nay!");
        }
        OptionItemChild optionItemChild = optionItemChildService.createOptionItemChild(optionItemChildDTO);
        return ResponseEntity.created(new URI("/api/option-item-child"))
                .headers(HeaderUtil.createAlert(applicationName,  "A optionItemChild is created with identifier " + optionItemChild.getId(), optionItemChild.getName()))
                .body(optionItemChild);
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= ManagerRestaurantVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagerRestaurantVM.PASSWORD_MAX_LENGTH;
    }
}
