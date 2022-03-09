package com.university.lele.score.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "mutual_evaluation_info")
public class MutualEvaluationInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "username")
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "star_num")
    private Integer starNum;

    @Column(name = "comment")
    private String comment;

    @Column(name = "mark_student_id")
    private String markStudentId;

    @Column(name = "teacher_id")
    private String teacherId;

    @Column(name = "group_name")
    private Integer groupName;

    @Column(name = "group_type")
    private Integer groupType;

}
