package com.university.lele.account.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "teacher_configuration")
public class TeacherConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "teacher_id")
    private String teacherId;

    /**
     * 0是关，1是开
     */
    @Column(name = "score_show_switch")
    private Integer scoreShowSwitch;

    @Column(name = "station")
    private String station;

    @Column(name = "extent")
    private String extent;

    @Column(name = "sign_in_date")
    private String signInDate;

}
