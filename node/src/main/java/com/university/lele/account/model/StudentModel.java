package com.university.lele.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentModel {

    private String id;

    private String username;
    private String name;
    private String password;
    private int grade;


    private boolean groupType;//YES or NO//YES表示是组长，NO表示不是组长

    private int groupName;

    private String email;

    private String head;

    private String phone;

    /**
     * 指导教师
     */
    private String teacherName;

    /**
     * 指导教师id
     */
    private String teacherId;
    private String schoolId;
    private String schoolName;
    private String qrCode;

}
