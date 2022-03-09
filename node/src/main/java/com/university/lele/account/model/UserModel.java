package com.university.lele.account.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private String id;
    private String password;
    private String oldPassword;
    private String phone;
    private MultipartFile file;
    private String avatar;
    private int userType;

    private String latitude;
    private String longitude;


}
