package com.pap.service.dto;

import com.pap.domain.Courier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * A DTO representing a user, with his authorities.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourierDTO {

    private String id;

    //@NotBlank
    //@Pattern(regexp = Constants.PHONE_REGEX)
    private String phone;

    @Size(max = 50)
    private String fullName;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    private String soCMND;

    private String licensePlate;

    @Size(max = 256)
    private String avatar;

    private boolean activated = false;

    private String createdBy;

    private LocalDateTime createdDate;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedDate;

    public CourierDTO(Courier courier) {
        this.id = courier.getId();
        this.phone = courier.getPhone();
        this.fullName = courier.getFullName();
        this.email = courier.getEmail();
        this.avatar = courier.getAvatar();
        this.licensePlate = courier.getLicensePlate();
        this.soCMND = courier.getSoCMND();
        this.activated = courier.isActivated();
        this.createdBy = courier.getCreatedBy();
        this.createdDate = courier.getCreatedDate();
        this.lastModifiedBy = courier.getLastModifiedBy();
        this.lastModifiedDate = courier.getLastModifiedDate();
    }

    @Override
    public String toString() {
        return "CourierDTO{" +
            "id='" + id + '\'' +
            ", phone='" + phone + '\'' +
            ", fullName='" + fullName + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + avatar + '\'' +
            ", activated=" + activated +
            ", createdBy='" + createdBy + '\'' +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            '}';
    }
}
