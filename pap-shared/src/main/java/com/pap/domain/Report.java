package com.pap.domain;

import com.pap.config.Constants;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "report")
@Data
public class Report {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "CHAR(36)", updatable = false, nullable = false)
    private String id = null;

    @Column(name = "content", length = 1000)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_user")
    private Constants.TypeUser typeUser;

    @Column(name = "user_id")
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_subject")
    private Constants.TypeUser typeUserSubject;

    @Column(name = "subject_id")
    private String subjectId;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
