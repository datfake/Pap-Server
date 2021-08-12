package com.pap.service.dto;

import com.pap.domain.OptionItemChild;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionItemChildDTO {

    private String id;
    private String name;
    private BigDecimal price;
    private boolean activated;
    private String restaurantEmail;
    private String optionItemId;

    public OptionItemChildDTO(OptionItemChild optionItemChild) {
        this.id = optionItemChild.getId();
        this.name = optionItemChild.getName();
        this.price = optionItemChild.getPrice();
        this.activated = optionItemChild.isActivated();
        this.restaurantEmail = optionItemChild.getRestaurantEmail();
        this.optionItemId = optionItemChild.getOptionItem().getId();
    }
}
