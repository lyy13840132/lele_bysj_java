package com.university.lele.account.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SettingUserModel {
    private String id;
    private String password;
    private String phone;
    private String userType;
}
