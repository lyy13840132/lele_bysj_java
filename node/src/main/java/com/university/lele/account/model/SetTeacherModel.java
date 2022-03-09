package com.university.lele.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SetTeacherModel {
    private String teacherId;
    private String signInDate;
    private String extent;
    private int scoreShowSwitch;
}
