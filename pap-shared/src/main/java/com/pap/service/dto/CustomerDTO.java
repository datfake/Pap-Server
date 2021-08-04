package com.pap.service.dto;

import com.pap.config.Constants;
import com.pap.domain.Customer;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * A DTO representing a user, with his authorities.
 */
@Data
public class CustomerDTO {

    private String id;

    private String phone;

    @Size(max = 50)
    private String fullName;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    @Size(max = 256)
    private String avatar;

    private boolean activated = false;

    private String createdBy;

    private LocalDateTime createdDate;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedDate;


    public CustomerDTO() {
        // Empty constructor needed for Jackson.
    }
    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.phone = customer.getPhone();
        this.fullName = customer.getFullName();
        this.email = customer.getEmail();
        this.avatar = customer.getAvatar();
        this.activated = customer.isActivated();
        this.createdBy = customer.getCreatedBy();
        this.createdDate = customer.getCreatedDate();
        this.lastModifiedBy = customer.getLastModifiedBy();
        this.lastModifiedDate = customer.getLastModifiedDate();
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
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
