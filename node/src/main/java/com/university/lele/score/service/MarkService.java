package com.university.lele.score.service;

import com.university.lele.account.model.TeacherModel;
import com.university.lele.global.Result;
import com.university.lele.score.model.TeacherMarkModel;
import org.springframework.stereotype.Service;

@Service
public interface MarkService {
    Result add(TeacherMarkModel teacherMarkModel);


    Result findMarkInfoById(String studentId);

    Result findAllMarkInfoById(String teacherId);

    Result statisticsMark(String teacherId);
}
