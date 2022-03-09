package com.university.lele.score.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecordModel {
    private String studentId;
    private String teacherId;
    private String record;
    private String image;
    private String date;

}
