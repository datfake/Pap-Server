package com.pap.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "complain")
@Data
public class Complain extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "CHAR(36)", updatable = false, nullable = false)
    private String id = null;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "customer_phone")
    private String customerPhone;

    @Column(name = "restaurant_id")
    private String restaurantId;

    @Column(name = "order_id")
    private String orderId;
}