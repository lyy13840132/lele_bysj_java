package com.university.lele.account.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;//状态

@Data
@Entity
@Table(name = "teacherinfo")
public class Teacherinfo implements Serializable {

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


    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;


    @Column(name = "school_id")
    private String schoolId;

    @Column(name = "user_type")
    private int userType;
    @Column(name = "avatar", nullable = false)
    private String avatar;

    @Column(name = "school_name")
    private String schoolName;

}
