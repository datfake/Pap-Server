package com.pap.service.dto;

import com.pap.config.Constants;
import com.pap.domain.ManagerRestaurant;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.time.Instant;

/**
 * A DTO representing a user, with his authorities.
 */
@Data
public class ManagerRestaurantDTO {

    private String id;

    // @Pattern(regexp = Constants.PHONE_REGEX)
    private String phone;

    @Size(max = 50)
    private String fullName;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    @NotNull
    @Column(name = "name_restaurant", length = 500)
    private String nameRestaurant;

    @Column(name = "summary")
    private String summary;

    @Column(name = "content")
    private String content;

    @Column(name = "so_dkkd")
    private String soDKKD;

    @NotNull
    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "status")
    private boolean status = false;

    @NotNull
    @Column(name = "is_partner")
    private boolean isPartner=false;

    @Size(max = 256)
    private String imageUrl;

    private boolean activated = false;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;


    public ManagerRestaurantDTO() {
        // Empty constructor needed for Jackson.
    }
    public ManagerRestaurantDTO(ManagerRestaurant managerRestaurant) {
        this.id = managerRestaurant.getId();
        this.phone = managerRestaurant.getPhone();
        this.fullName = managerRestaurant.getFullName();
        this.email = managerRestaurant.getEmail();
        this.nameRestaurant = managerRestaurant.getNameRestaurant();
        this.summary = managerRestaurant.getSummary();
        this.content = managerRestaurant.getContent();
        this.soDKKD = managerRestaurant.getSoDKKD();
        this.address = managerRestaurant.getAddress();
        this.status = managerRestaurant.isStatus();
        this.isPartner = managerRestaurant.isPartner();
        this.imageUrl = managerRestaurant.getImageUrl();
        this.activated = managerRestaurant.isActivated();
        this.createdBy = managerRestaurant.getCreatedBy();
        this.createdDate = managerRestaurant.getCreatedDate();
        this.lastModifiedBy = managerRestaurant.getLastModifiedBy();
        this.lastModifiedDate = managerRestaurant.getLastModifiedDate();
    }
}
