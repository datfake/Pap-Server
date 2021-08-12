package com.pap.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pap.config.Constants;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A Manager Courier.
 */
@Entity
@Table(name = "courier")
@Data
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties(value = {"orders"})
public class Courier extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "CHAR(36)", updatable = false, nullable = false)
    private String id = null;

    @NotNull
    // @Pattern(regexp = Constants.PHONE_REGEX)
    @Column(length = 20, unique = true, nullable = false)
    private String phone;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @Column(name = "full_name", length = 250)
    private String fullName;

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private String email;

    @Column(name = "address", length = 500)
    private String address;

    @Size(max = 12)
    @Column(name = "so_cmnd", length = 256)
    private String soCMND;

    @Size(max = 256)
    @Column(name = "image_first_cmnd", length = 256)
    private String imageFirstCMND;

    @Size(max = 256)
    @Column(name = "image_last_cmnd", length = 256)
    private String imageLastCMND;

    @Size(max = 12)
    @Column(name = "so_cccd", length = 256)
    private String soCCCD;

    @Size(max = 256)
    @Column(name = "image_first_cccd", length = 256)
    private String imageFirstCCCD;

    @Size(max = 256)
    @Column(name = "image_last_cccd", length = 256)
    private String imageLastCCCD;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    @Column(name = "date_cmnd")
    private LocalDate dateCMND;

    @Column(name = "bank_number", length = 20)
    private String bankNumber;

    @Column(name = "name_bank", length = 256)
    private String nameBank;

    @Column(name = "full_name_bank", length = 256)
    private String fullNameBank;

    @Column(name = "branch_bank", length = 256)
    private String branchBank;

    @Column(name="license_plate", unique = true, nullable = false)
    private String licensePlate;

    @Size(max = 256)
    @Column(name = "image_gplx", length = 256)
    private String imageGPLX;

    @Size(max = 256)
    @Column(name = "image_cavet", length = 256)
    private String imageCavet;

    @Size(max = 256)
    @Column(name = "image_motorbike", length = 256)
    private String imageMotorbike;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private Constants.CourierStatus status = Constants.CourierStatus.OFFLINE;

    @NotNull
    @Column(nullable = false)
    private boolean activated = true;

    @Size(max = 256)
    @Column(name = "avatar", length = 256)
    private String avatar;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "courier", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Order> orders = new LinkedHashSet<>(0);

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Courier)) {
            return false;
        }
        return id != null && id.equals(((Courier) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
