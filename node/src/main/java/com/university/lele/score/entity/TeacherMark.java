package com.university.lele.score.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "teacher_mark")
public class TeacherMark implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "teacher_id")
    private String teacherId;

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "rank")
    private Integer rank;

    @Column(name = "date")
    private String date;

}
