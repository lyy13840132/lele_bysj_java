package com.university.lele.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentLatAndLonModel {
    private String studentId;
    private String latitude;
    private String longitude;
}
