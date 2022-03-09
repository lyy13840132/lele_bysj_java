package com.university.lele.score.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherMarkModel {
    private String teacherId;
    private String studentId;
    private int rank;
   // private String date;
}
