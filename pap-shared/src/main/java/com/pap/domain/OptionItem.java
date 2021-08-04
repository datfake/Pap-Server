package com.pap.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "option_item")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = {"optionItemChildren"})
public class OptionItem extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "CHAR(36)", updatable = false, nullable = false)
    private String id = null;

    @NotNull
    @Column(name = "name", length = 256)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "optionItem", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<OptionItemChild> optionItemChildren = new LinkedHashSet<>(0);
}
