package com.pap.domain;

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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
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

    @Size(min = 9, max = 12)
    @Column(name="so_cmnd", unique = true, nullable = false)
    @NotNull
    private String soCMND;

    @Column(name="license_plate", unique = true, nullable = false)
    @NotNull
    private String licensePlate;

    @Column(nullable = false)
    @NotNull
    private Constants.CourierStatus status = Constants.CourierStatus.OFFLINE;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

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

    // prettier-ignore


    @Override
    public String toString() {
        return "Courier{" +
            "id='" + id + '\'' +
            ", phone='" + phone + '\'' +
            ", password='" + password + '\'' +
            ", fullName='" + fullName + '\'' +
            ", email='" + email + '\'' +
            ", activated=" + activated +
            ", avatar='" + avatar + '\'' +
            '}';
    }
}
