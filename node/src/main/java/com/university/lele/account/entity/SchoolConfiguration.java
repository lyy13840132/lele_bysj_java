package com.university.lele.account.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "school_configuration")
public class SchoolConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "school_id")
    private String schoolId;

    /**
     * 0是关闭，1是开
     */
    @Column(name = "assessment_switch")
    private Integer assessmentSwitch;


}
