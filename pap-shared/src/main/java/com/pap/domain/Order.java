package com.pap.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pap.config.Constants;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "order_product")
@Data
@JsonIgnoreProperties(value = {"orderDetails"})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Order extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "CHAR(36)", updatable = false, nullable = false)
    private String id = null;

    @NotNull
    @Column(name = "price_origin")
    private BigDecimal priceOrigin;

    @Column(name = "price_service")
    private BigDecimal priceService;

    @NotNull
    @Column(name = "name_order")
    private String nameOrder;

    @NotNull
    @Column(length = 20)
    private String phone;

    @NotNull
    @Column(name = "shipping_fee")
    private BigDecimal shipping_fee;

    @NotNull
    @Column(name = "range")
    private float range;

    @Column(name = "discount_price")
    private BigDecimal discountPrice;

    @Column(name = "hand_delivery")
    private BigDecimal handDelivery;

    @NotNull
    @Column(name = "price_total")
    private BigDecimal priceTotal;

    @NotNull
    @Column(nullable = false)
    private boolean paid = false;

    @Column(name = "name_courier")
    private String nameCourier;

    @Column(nullable = false)
    @NotNull
    private Constants.OrderStatus status = Constants.OrderStatus.DANGCHON;

    @Column(name = "note", length = 500)
    private String note;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courier_id", nullable = false)
    private Courier courier;

    @Column(name = "discount_id")
    private String discountId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<OrderDetail> orderDetails = new LinkedHashSet<>(0);
}
