package com.university.lele.siginIn.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "sign_in")
public class SignIn implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "student_id")
    private String studentId;

    /**
     * 正常打卡
     */
    @Column(name = "normal_status_all")
    private Integer normalStatusAll;

    /**
     * 迟到打卡
     */
    @Column(name = "late_status_all")
    private Integer lateStatusAll;

    /**
     * 外勤正常打卡
     */
    @Column(name = "normal_field_all")
    private Integer normalFieldAll;

    /**
     * 外勤迟到打卡
     */
    @Column(name = "late_field_all")
    private Integer lateFieldAll;

}
