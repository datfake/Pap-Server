package com.pap.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pap.domain.Discount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.pap.domain.AbstractAuditingEntity.DATE_TIME_PATTERN;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDTO {

    private String id;
    private String code;
    private String title;
    private String content;
    private BigDecimal price;
    private int sale;
    private String restaurantEmail;
    private BigDecimal minOrderPrice;
    private int quantity;
    private int quantityDay;
    private int quantityCustomer;
    private int quantityCustomerDay;
    private String imageUrl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private LocalDateTime fromDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private LocalDateTime toDate;

    public DiscountDTO(Discount discount) {
        this.id = discount.getId();
        this.code = discount.getCode();
        this.title = discount.getTitle();
        this.content = discount.getContent();
        this.price = discount.getPrice();
        this.sale = discount.getSale();
        this.minOrderPrice = discount.getMinOrderPrice();
        this.quantity = discount.getQuantity();
        this.quantityDay = discount.getQuantityDay();
        this.quantityCustomer = discount.getQuantityCustomer();
        this.quantityCustomerDay = discount.getQuantityCustomerDay();
        this.imageUrl = discount.getImageUrl();
    }
}
