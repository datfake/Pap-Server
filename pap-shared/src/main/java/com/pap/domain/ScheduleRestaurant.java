package com.pap.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;

@Entity
@Table(name = "schedule_restaurant")
@Data
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ScheduleRestaurant extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String TIME_PATTERN = "HH:mm:ss";

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "CHAR(36)", updatable = false, nullable = false)
    private String id = null;

    @Column(name = "from_monday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_PATTERN)
    private LocalTime fromMonday;

    @Column(name = "to_monday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_PATTERN)
    private LocalTime toMonday;

    @Column(name = "from_tuesday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_PATTERN)
    private LocalTime fromTuesday;

    @Column(name = "to_tuesday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_PATTERN)
    private LocalTime toTuesday;

    @Column(name = "from_wednesday ")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_PATTERN)
    private LocalTime fromWednesday;

    @Column(name = "to_wednesday ")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_PATTERN)
    private LocalTime toWednesday;

    @Column(name = "from_thursday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_PATTERN)
    private LocalTime fromThursday;

    @Column(name = "to_thursday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_PATTERN)
    private LocalTime toThursday ;

    @Column(name = "from_friday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_PATTERN)
    private LocalTime fromFriday;

    @Column(name = "to_friday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_PATTERN)
    private LocalTime toFriday;

    @Column(name = "from_Saturday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_PATTERN)
    private LocalTime fromSaturday;

    @Column(name = "to_Saturday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_PATTERN)
    private LocalTime toSaturday;

    @Column(name = "from_sunday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_PATTERN)
    private LocalTime fromSunday;

    @Column(name = "to_sunday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_PATTERN)
    private LocalTime toSunday;

    @Column(name = "list_day_off")
    private String listDayOff;

    @OneToOne
    @JoinColumn(name = "restaurant_id")
    private ManagerRestaurant managerRestaurant;
}
