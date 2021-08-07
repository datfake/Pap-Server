package com.pap.service;

import com.pap.domain.Discount;
import com.pap.repository.DiscountRepository;
import com.pap.service.dto.DiscountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DiscountService {

    private final Logger log = LoggerFactory.getLogger(DiscountService.class);

    private final DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
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
        discountRepository.save(discount);
        log.debug("Created Information for Discount: {}", discount);
        return discount;
    }
}
