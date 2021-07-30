package com.pap.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pap.config.Constants;
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
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A Manager Restaurant.
 */
@Entity
@Table(name = "manager_restaurant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties(value = {"items", "categories", "reviews", "discounts"})
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

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @ManyToMany(mappedBy = "restaurants", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToStringPlugin.Exclude
    private Set<Category> categories;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Item> items = new LinkedHashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Review> reviews = new LinkedHashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Discount> discounts = new LinkedHashSet<>(0);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNameRestaurant() {
        return nameRestaurant;
    }

    public void setNameRestaurant(String nameRestaurant) {
        this.nameRestaurant = nameRestaurant;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSoDKKD() {
        return soDKKD;
    }

    public void setSoDKKD(String soDKKD) {
        this.soDKKD = soDKKD;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isPartner() {
        return isPartner;
    }

    public void setPartner(boolean partner) {
        isPartner = partner;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Set<Discount> discounts) {
        this.discounts = discounts;
    }

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
            ", imageUrl='" + imageUrl + '\'' +
            '}';
    }
}
