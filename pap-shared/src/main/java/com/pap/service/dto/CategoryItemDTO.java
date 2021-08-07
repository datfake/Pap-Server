package com.pap.service.dto;

import com.pap.domain.CategoryItem;
import lombok.Data;

@Data
public class CategoryItemDTO {

    private String id;
    private String name;
    private String restaurantEmail;

    public CategoryItemDTO(CategoryItem categoryItem) {
        this.id = categoryItem.getId();
        this.name = categoryItem.getName();
        this.restaurantEmail = categoryItem.getRestaurant().getEmail();
    }
}
