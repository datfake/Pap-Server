package com.pap.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "discount")
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Discount extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "CHAR(36)", updatable = false, nullable = false)
    private String id = null;

    @NotNull
    @Column(name = "code", length = 10)
    private String code;

    @NotNull
    @Column(name = "title", length = 256)
    private String title;

    @NotNull
    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "sale")
    private int sale;

    @Column(name = "min_order_price")
    private BigDecimal minOrderPrice;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "quantity_day")
    private int quantityDay;

    @Column(name = "quantity_customer")
    private int quantityCustomer;

    @Column(name = "quantity_customer_day")
    private int quantityCustomerDay;

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private ManagerRestaurant restaurant;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "from_date", length = 50)
    private LocalDateTime fromDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "to_date", length = 50)
    private LocalDateTime toDate;

    @Column(name = "is_deleted")
    private short isDeleted = 0;
}
