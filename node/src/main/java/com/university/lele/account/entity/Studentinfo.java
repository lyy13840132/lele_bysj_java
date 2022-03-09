package com.university.lele.account.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "studentinfo")
public class Studentinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "grade", nullable = false)
    private Integer grade;

    /**
     * 0表示组长，1表示组员
     */
    @Column(name = "group_type", nullable = false)
    private Integer groupType;

    /**
     * 在哪个组
     */
    @Column(name = "group_name", nullable = false)
    private Integer groupName;

    @Column(name = "email")
    private String email;



    @Column(name = "phone")
    private String phone;

    /**
     * 指导教师
     */
    @Column(name = "teacher_name", nullable = false)
    private String teacherName;

    /**
     * 指导教师id
     */
    @Column(name = "teacher_id", nullable = false)
    private String teacherId;


    @Column(name = "user_type", nullable = false)
    private int userType;

    @Column(name = "school_id", nullable = false)
    private String schoolId;

    @Column(name = "school_name", nullable = false)
    private String schoolName;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "qr_code")
    private String qrCode;
    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;



}
