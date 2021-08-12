package com.pap.service.dto;

import com.pap.domain.OptionItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionItemDTO {

    private String id;
    private String name;
    private String restaurantEmail;
    private String itemId;
    private boolean activated;

    public OptionItemDTO(OptionItem optionItem) {
        this.id = optionItem.getId();
        this.name = optionItem.getName();
        this.restaurantEmail = optionItem.getRestaurantEmail();
        this.itemId = optionItem.getItem().getId();
    }
}
