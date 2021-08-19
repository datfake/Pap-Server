package com.pap.service.dto;

import com.pap.domain.ManagerRestaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {
    private String id;
    private String nameRestaurant;
    private String address;
    private String avatar;
    private float rate;
    private Set<String> titleDiscounts;

    public RestaurantDTO(ManagerRestaurant managerRestaurant) {
        this.id = managerRestaurant.getId();
        this.nameRestaurant = managerRestaurant.getNameRestaurant();
        this.address = managerRestaurant.getAddress();
        this.rate = managerRestaurant.getRate();
        this.avatar = managerRestaurant.getAvatar();
    }
}
