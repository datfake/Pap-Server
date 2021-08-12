package com.pap.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "item")
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = {"optionItems"})
public class Item extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "CHAR(36)", updatable = false, nullable = false)
    private String id = null;

    @NotNull
    @Column(name = "name", length = 256)
    private String name;

    @Column(name = "summary", length = 500)
    private String summary;

    @Column(name = "content", length = 1000)
    private String content;

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @NotNull
    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private int quantity;

    @NotNull
    @Column(name = "count_ordered")
    private int countOrdered = 0;

    @Column(name = "restaurant_email")
    private String restaurantEmail;

    @Column(nullable = false)
    private boolean activated = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryItem_id", nullable = false)
    private CategoryItem categoryItem;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<OptionItem> optionItems = new LinkedHashSet<>(0);
}
