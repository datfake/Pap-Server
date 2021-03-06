package com.pap.domain;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "notice")
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Notice extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "CHAR(36)", updatable = false, nullable = false)
    private String id = null;

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;
}
