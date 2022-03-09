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
public class TeacherModel {

    private String id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String name;
    private String password;
    private String email;
    private String phone;
    private String schoolId;
    private int userType;
    private String schoolName;
}
