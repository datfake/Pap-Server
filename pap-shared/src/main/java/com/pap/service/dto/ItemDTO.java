package com.pap.service.dto;

import com.pap.domain.Item;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemDTO {

    private String id;
    private String name;
    private String summary;
    private String content;
    private String imageUrl;
    private BigDecimal price;
    private int quantity;
    private int countOrdered;
    private boolean activated;
    private String restaurantEmail;
    private String categoryItemId;

    public ItemDTO(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.summary = item.getSummary();
        this.content = item.getContent();
        this.imageUrl = item.getImageUrl();
        this.price = item.getPrice();
        this.quantity = item.getQuantity();
        this.countOrdered = item.getCountOrdered();
        this.activated = item.isActivated();
        this.restaurantEmail = item.getRestaurantEmail();
        this.categoryItemId = item.getCategoryItem().getId();
    }
}
