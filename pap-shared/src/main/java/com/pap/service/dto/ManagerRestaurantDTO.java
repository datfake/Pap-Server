package com.pap.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pap.config.Constants;
import com.pap.domain.ManagerRestaurant;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static com.pap.domain.AbstractAuditingEntity.DATE_PATTERN;
import static com.pap.domain.AbstractAuditingEntity.DATE_TIME_PATTERN;

/**
 * A DTO representing a ManagerRestaurantDTO, with his authorities.
 */
@Data
public class ManagerRestaurantDTO {

    private String id;

    // @Pattern(regexp = Constants.PHONE_REGEX)
    private String phone;

    private String fullName;

    private String email;

    private String nameRestaurant;

    private String summary;

    private String content;

    private String soDKKD;

    private String address;

    private float rate;

    private boolean status;

    private int sharing;

    private boolean isPartner;

    private String avatar;

    private boolean activated;

    private String imageRestaurant;

    private Constants.TypeBusiness typeBusiness;

    private String soCMND;

    private String imageFirstCMND;

    private String imageLastCMND;

    private String soCCCD;

    private String imageFirstCCCD;

    private String imageLastCCCD;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private LocalDate dateCMND;

    private String bankNumber;

    private String nameBank;

    private String fullNameBank;

    private String branchBank;

    private Constants.RoleManagerRestaurant roleManagerRestaurant;

    private Set<String> categories;

    private String createdBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private LocalDateTime createdDate;

    private String lastModifiedBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private LocalDateTime lastModifiedDate;


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
        this.rate = managerRestaurant.getRate();
        this.status = managerRestaurant.isStatus();
        this.isPartner = managerRestaurant.isPartner();
        this.sharing = managerRestaurant.getSharing();
        this.avatar = managerRestaurant.getAvatar();
        this.activated = managerRestaurant.isActivated();
        this.imageRestaurant = managerRestaurant.getNameRestaurant();
        this.typeBusiness = managerRestaurant.getTypeBusiness();
        this.soCMND = managerRestaurant.getSoCMND();
        this.imageFirstCMND = managerRestaurant.getImageFirstCMND();
        this.imageLastCMND = managerRestaurant.getImageLastCMND();
        this.soCCCD = managerRestaurant.getSoCCCD();
        this.imageFirstCCCD = managerRestaurant.getImageFirstCCCD();
        this.imageLastCCCD = managerRestaurant.getImageLastCCCD();
        this.dateCMND = this.getDateCMND();
        this.bankNumber = this.getBankNumber();
        this.nameBank = this.getNameBank();
        this.fullNameBank = this.getFullNameBank();
        this.branchBank = this.getBranchBank();
        this.createdBy = managerRestaurant.getCreatedBy();
        this.createdDate = managerRestaurant.getCreatedDate();
        this.lastModifiedBy = managerRestaurant.getLastModifiedBy();
        this.lastModifiedDate = managerRestaurant.getLastModifiedDate();
    }
}
