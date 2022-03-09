package com.university.lele.cnki.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "cnki_high_sim")
public class CnkiHighSim implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "username_sim")
    private String usernameSim;

    @Column(name = "name")
    private String name;

    @Column(name = "name_sim")
    private String nameSim;

    @Column(name = "sim")
    private String sim;

    @Column(name = "date")
    private String date;

}
