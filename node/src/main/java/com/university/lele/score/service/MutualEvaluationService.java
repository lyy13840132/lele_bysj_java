package com.university.lele.score.service;

import com.university.lele.global.Result;
import com.university.lele.score.model.MutualEvaluationModel;
import org.springframework.stereotype.Service;

@Service
public interface MutualEvaluationService {

    Result mutualEvaluation(MutualEvaluationModel evaluationModel);

    Result findMutualInfo(String studentId);

    Result studentRanking(String teacherId, int groupName);

    Result allMutualInfos(String teacherId);

    Result sortMutual(String teacherId);
}
