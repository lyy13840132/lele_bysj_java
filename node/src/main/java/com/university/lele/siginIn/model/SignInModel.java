package com.university.lele.siginIn.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInModel {
    //private String id;
    private String date;
    private String station;
    private String address;
    private int status;
    private String studentId;
}
