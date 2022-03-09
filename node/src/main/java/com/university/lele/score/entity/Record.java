package com.university.lele.score.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "record")
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "record")
    private String record;

    @Column(name = "image")
    private String image;

    @Column(name = "teacher_id")
    private String teacherId;

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "date")
    private String date;

}
