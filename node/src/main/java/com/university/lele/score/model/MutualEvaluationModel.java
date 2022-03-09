package com.university.lele.score.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MutualEvaluationModel {


    private String markStudentId;
    private List<StudentMutualScore> studentMutualScores;

}
