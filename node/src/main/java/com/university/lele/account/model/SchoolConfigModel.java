package com.university.lele.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SchoolConfigModel {
    private String schoolId;
    private int assessmentSwitch;

}
