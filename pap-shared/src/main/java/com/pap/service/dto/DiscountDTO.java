package com.pap.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.pap.domain.AbstractAuditingEntity.DATE_TIME_PATTERN;

@Data
public class DiscountDTO {

    private String id;
    private String code;
    private String title;
    private String content;
    private BigDecimal price;
    private int sale;
    private String restaurantId;
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
}
