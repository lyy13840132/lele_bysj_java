package com.university.lele.score.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentMutualScore {
    private String studentId;
    private int starNum;
    private String comment;
}
