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
public class CreateSchoolModel {
    @NotEmpty(message = "学校名称不能为空！")
    private String name;
    @NotEmpty(message = "学校帐号不能为空！")
    private  String username;
    private String password;
    private String id;
    private int userType;


}
