package com.pap.web.rest;

import com.pap.domain.*;
import com.pap.exception.BadRequestAlertException;
import com.pap.exception.InvalidPasswordException;
import com.pap.repository.ManagerRestaurantRepository;
import com.pap.security.AuthoritiesConstants;
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
import org.springframework.security.access.prepost.PreAuthorize;
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
public class RestaurantController {

    @Value("${pap.clientApp.name}")
    private String applicationName;

    private static class AccountResourceException extends RuntimeException {
        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(RestaurantController.class);

    private final ManagerRestaurantRepository managerRestaurantRepository;

    private final ManagerRestaurantService managerRestaurantService;

    private final DiscountService discountService;

    private final CategoryItemService categoryItemService;

    private final ItemService itemService;

    private final OptionItemService optionItemService;

    private final OptionItemChildService optionItemChildService;

    private final SmsService smsService;

    public RestaurantController(ManagerRestaurantRepository managerRestaurantRepository, ManagerRestaurantService managerRestaurantService, MailService mailService, DiscountService discountService, CategoryItemService categoryItemService, ItemService itemService, OptionItemService optionItemService, OptionItemChildService optionItemChildService, SmsService smsService) {
        this.managerRestaurantRepository = managerRestaurantRepository;
        this.managerRestaurantService = managerRestaurantService;
        this.discountService = discountService;
        this.categoryItemService = categoryItemService;
        this.itemService = itemService;
        this.optionItemService = optionItemService;
        this.optionItemChildService = optionItemChildService;
        this.smsService = smsService;
    }

    @PostMapping(path = "/discount")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Discount> createDiscount(@RequestBody DiscountDTO discountDTO) throws URISyntaxException {
        if (discountDTO.getId() != null) {
            throw new BadRequestAlertException("A new Discount cannot already have an ID", "discount", "idexists");
        }
        Discount discount = discountService.createDiscount(discountDTO);
        return ResponseEntity.created(new URI("/api/discount/" + discount.getCode()))
            .headers(HeaderUtil.createAlert(applicationName,  "A courier is created with identifier " + discount.getId(), discount.getCode()))
            .body(discount);
    }

    @PostMapping(path = "/category-item")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<CategoryItem> createCategoryItem(@RequestBody CategoryItemDTO categoryItemDTO) throws URISyntaxException {
        if (categoryItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new CategoryItem cannot already have an ID", "discount", "idexists");
        }
        CategoryItem categoryItem = categoryItemService.createCategoryItem(categoryItemDTO);
        return ResponseEntity.created(new URI("/api/category-item"))
                .headers(HeaderUtil.createAlert(applicationName,  "A CategoryItem is created with identifier " + categoryItem.getId(), categoryItem.getName()))
                .body(categoryItem);
    }

    @PostMapping(path = "/item")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Object> createItem(@RequestBody ItemDTO itemDTO) throws URISyntaxException {
        Item item = itemService.createItem(itemDTO);
        return ResponseEntity.created(new URI("/api/item"))
                .headers(HeaderUtil.createAlert(applicationName,  "A Item is created with identifier " + item.getId(), item.getName()))
                .body(item);
    }

    @PostMapping(path = "/option-item")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Object> createOptionItem(@RequestBody OptionItemDTO optionItemDTO) throws URISyntaxException {
        OptionItem optionItem = optionItemService.createOptionItem(optionItemDTO);
        return ResponseEntity.created(new URI("/api/option-item"))
                .headers(HeaderUtil.createAlert(applicationName,  "A optionItem is created with identifier " + optionItem.getId(), optionItem.getName()))
                .body(optionItem);
    }

    @PostMapping(path = "/option-item-child")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Object> createOptionItemChild(@RequestBody OptionItemChildDTO optionItemChildDTO) throws URISyntaxException {
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
