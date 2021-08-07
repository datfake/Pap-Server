package com.pap.service.dto;

import lombok.Data;

import java.util.Set;

@Data
public class RestaurantDTO {
    private String id;
    private String nameRestaurant;
    private String address;
    private String avatar;
    private float rate;
    private Set<String> titleDiscounts;
}
