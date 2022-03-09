package com.university.lele.score.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "team_score")
public class TeamScore implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "type")
    private Integer type;

    @Column(name = "rank")
    private String rank;

    @Column(name = "commenton")
    private String commenton;

    @Column(name = "student_id")
    private String studentId;




    @Override
    public String toString() {
        return "TeamScore{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", type=" + type +
                ", rank='" + rank + '\'' +
                ", commenton='" + commenton + '\'' +
                ", studentId='" + studentId + '\'' +
                '}';
    }
}
