package com.university.lele.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginModel implements Serializable {


    @NotEmpty(message = "帐号不能为空！")
    private String username;
    @NotEmpty(message = "密码不能为空！")
    private String password;
    private int userType;

}
