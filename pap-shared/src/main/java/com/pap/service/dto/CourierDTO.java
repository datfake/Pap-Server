package com.pap.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pap.domain.Courier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.pap.domain.AbstractAuditingEntity.DATE_PATTERN;
import static com.pap.domain.AbstractAuditingEntity.DATE_TIME_PATTERN;

/**
 * A DTO representing a user, with his authorities.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourierDTO {

    private String id;

    private String phone;

    private String fullName;

    private String email;

    private String address;

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

    private String licensePlate;

    private String imageGPLX;

    private String imageCavet;

    private String imageMotorbike;

    private String avatar;

    private boolean activated;

    private String createdBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private LocalDateTime createdDate;

    private String lastModifiedBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private LocalDateTime lastModifiedDate;

    public CourierDTO(Courier courier) {
        this.id = courier.getId();
        this.phone = courier.getPhone();
        this.fullName = courier.getFullName();
        this.email = courier.getEmail();
        this.address = courier.getAddress();
        this.soCMND = courier.getSoCMND();
        this.imageFirstCMND = courier.getImageFirstCMND();
        this.imageLastCMND = courier.getImageLastCMND();
        this.soCCCD = courier.getSoCCCD();
        this.imageFirstCCCD = courier.getImageFirstCCCD();
        this.imageLastCCCD = courier.getImageLastCCCD();
        this.dateCMND = courier.getDateCMND();
        this.bankNumber = courier.getBankNumber();
        this.nameBank = courier.getNameBank();
        this.fullNameBank = courier.getFullNameBank();
        this.branchBank = courier.getBranchBank();
        this.avatar = courier.getAvatar();
        this.licensePlate = courier.getLicensePlate();
        this.imageGPLX = courier.getImageGPLX();
        this.imageCavet = courier.getImageCavet();
        this.imageMotorbike = courier.getImageMotorbike();
        this.activated = courier.isActivated();
        this.createdBy = courier.getCreatedBy();
        this.createdDate = courier.getCreatedDate();
        this.lastModifiedBy = courier.getLastModifiedBy();
        this.lastModifiedDate = courier.getLastModifiedDate();
    }
}
