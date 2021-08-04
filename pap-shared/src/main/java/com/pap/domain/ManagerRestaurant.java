package com.pap.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pap.config.Constants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.bytebuddy.build.ToStringPlugin;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A Manager Restaurant.
 */
@Entity
@Table(name = "manager_restaurant")
@Data
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties(value = {"categoryItems", "categories", "reviews", "discounts"})
public class ManagerRestaurant extends AbstractAuditingEntity implements Serializable {

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

    @Column(name = "sharing")
    private int sharing;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Size(max = 256)
    @Column(name = "avatar", length = 256)
    private String avatar;

    @NotNull
    @Size(max = 256)
    @Column(name = "image_restaurant", length = 256)
    private String imageRestaurant;

    @NotNull
    @Column(name = "type_business")
    private Constants.TypeBusiness typeBusiness;

    @NotNull
    @Size(max = 12)
    @Column(name = "so_cmnd", length = 256)
    private String soCmnd;

    @NotNull
    @Size(max = 256)
    @Column(name = "image_first_cmnd", length = 256)
    private String imageFirstCmnd;

    @NotNull
    @Size(max = 256)
    @Column(name = "image_last_cmnd", length = 256)
    private String imageLastCmnd;

    @NotNull
    @Column(name = "date_cmnd")
    private LocalDate dateCmnd;

    @NotNull
    @Column(name = "bank_number", length = 20)
    private String bankNumber;

    @NotNull
    @Column(name = "name_bank", length = 256)
    private String nameBank;

    @NotNull
    @Column(name = "full_name_bank", length = 256)
    private String fullNameBank;

    @NotNull
    @Column(name = "branch_bank", length = 256)
    private String branchBank;

    @NotNull
    @Column(name = "role")
    private Constants.RoleManagerRestaurant roleManagerRestaurant;

    @ManyToMany(mappedBy = "restaurants", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToStringPlugin.Exclude
    private Set<Category> categories;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<CategoryItem> categoryItems = new LinkedHashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Review> reviews = new LinkedHashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Discount> discounts = new LinkedHashSet<>(0);

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ManagerRestaurant)) {
            return false;
        }
        return id != null && id.equals(((ManagerRestaurant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "ManagerRestaurant{" +
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
