package com.university.lele.siginIn.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "sign_in_info")
public class SignInInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date")
    private String date;

    @Column(name = "station")
    private String station;

    @Column(name = "address")
    private String address;

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "status")
    private Integer status;


}
