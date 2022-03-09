package com.university.lele.score.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "mutual_evaluation")
public class MutualEvaluation implements Serializable ,Comparable<MutualEvaluation>{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;
    @Column(name = "student_id")
    private String studentId;

    @Column(name = "star_num")
    private Integer starNum;

    @Column(name = "avg_star")
    private String avgStar;


    @Column(name = "date")
    private String date;

    @Column(name = "comment_list")
    private String commentList;

    @Column(name = "group_name")
    private Integer groupName;

    @Column(name = "group_type")
    private Integer groupType;

    @Column(name = "teacher_id")
    private String teacherId;


    @Override
    public int compareTo(MutualEvaluation o) {

        return this.starNum.compareTo(o.getStarNum());
    }


}
