package com.university.lele.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "controlpointdate")
public class Controlpointdate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "x")
    private String x;

    @Column(name = "y")
    private String y;

    @Column(name = "z")
    private String z;

    /**
     * 纬度
     */
    @Column(name = "latitude")
    private String latitude;

    /**
     * 经度
     */
    @Column(name = "longitude")
    private String longitude;

    @Column(name = "note")
    private String note;

    @Column(name = "state")
    private Integer state;

    @Column(name = "school_id")
    private String schoolId;

}
