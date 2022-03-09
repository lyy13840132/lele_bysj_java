package com.university.lele.cnki.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "cnki")
public class Cnki implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "username1")
    private String username1;

    @Column(name = "username2")
    private String username2;

    @Column(name = "name1")
    private String name1;

    @Column(name = "name2")
    private String name2;

    @Column(name = "sim")
    private String sim;

    @Column(name = "jaccard_sim")
    private String jaccardSim;

    @Column(name = "con_sim")
    private String conSim;

    /**
     * 是否是高度重复，1表示高度重复，0表示标准
     */
    @Column(name = "crib")
    private Integer crib;

    /**
     * 是否超过教师设定值，1表示超过，0表示未超过
     */
    @Column(name = "is_sim_rate")
    private int simRate;
    @Column(name = "teacher_id")
    private String teacherId;

}
