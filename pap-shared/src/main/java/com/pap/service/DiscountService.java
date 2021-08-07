package com.pap.service;

import com.pap.domain.Discount;
import com.pap.domain.ManagerRestaurant;
import com.pap.repository.DiscountRepository;
import com.pap.repository.ManagerRestaurantRepository;
import com.pap.service.dto.DiscountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class DiscountService {

    private final Logger log = LoggerFactory.getLogger(DiscountService.class);

    private final DiscountRepository discountRepository;

    private final ManagerRestaurantRepository managerRestaurantRepository;

    public DiscountService(DiscountRepository discountRepository, ManagerRestaurantRepository managerRestaurantRepository) {
        this.discountRepository = discountRepository;
        this.managerRestaurantRepository = managerRestaurantRepository;
    }

    public Discount createDiscount(DiscountDTO discountDTO) {
        Discount discount = new Discount();
        discount.setCode(discountDTO.getCode());
        discount.setTitle(discountDTO.getTitle());
        discount.setContent(discountDTO.getContent());
        discount.setPrice(discountDTO.getPrice());
        discount.setSale(discountDTO.getSale());
        discount.setMinOrderPrice(discountDTO.getMinOrderPrice());
        discount.setImageUrl(discountDTO.getImageUrl());
        discount.setQuantity(discountDTO.getQuantity());
        discount.setQuantityDay(discountDTO.getQuantityDay());
        discount.setQuantityCustomer(discountDTO.getQuantityCustomer());
        discount.setQuantityCustomerDay(discountDTO.getQuantityCustomerDay());
        discount.setFromDate(discountDTO.getFromDate());
        discount.setToDate(discountDTO.getToDate());
        Optional<ManagerRestaurant> restaurant =  managerRestaurantRepository.findOneByEmailIgnoreCase(discountDTO.getRestaurantEmail());
        discount.setRestaurant(restaurant.get());
        discount.setCreatedBy(discountDTO.getRestaurantEmail());
        discountRepository.save(discount);
        log.debug("Created Information for Discount: {}", discount);
        return discount;
    }

    public Optional<DiscountDTO> updateDiscount(DiscountDTO discountDTO) {
        return Optional.of(discountRepository
                .findById(discountDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(discount -> {
                    discount.setCode(discountDTO.getCode());
                    discount.setTitle(discountDTO.getTitle());
                    discount.setContent(discountDTO.getContent());
                    discount.setPrice(discountDTO.getPrice());
                    discount.setSale(discountDTO.getSale());
                    discount.setMinOrderPrice(discountDTO.getMinOrderPrice());
                    discount.setImageUrl(discountDTO.getImageUrl());
                    discount.setQuantity(discountDTO.getQuantity());
                    discount.setQuantityDay(discountDTO.getQuantityDay());
                    discount.setQuantityCustomer(discountDTO.getQuantityCustomer());
                    discount.setQuantityCustomerDay(discountDTO.getQuantityCustomerDay());
                    discount.setFromDate(discountDTO.getFromDate());
                    discount.setToDate(discountDTO.getToDate());
                    discount.setLastModifiedDate(LocalDateTime.now());
                    log.debug("Changed Information for discount: {}", discount);
                    return discount;
                })
                .map(DiscountDTO::new);
    }
}
