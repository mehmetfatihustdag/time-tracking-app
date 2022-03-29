package com.foodtech.timetracking.data.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "timetrack")
@Data
public class TimeTrack implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="trackdate")
    private LocalDate trackDate;

    @Column(name="punchin")
    private LocalDateTime punchIn;

    @Column(name="punchout")
    private LocalDateTime punchOut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee.id")
    private Employee employee;

}
