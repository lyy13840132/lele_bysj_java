package com.university.lele.score.service;

import com.university.lele.global.Result;
import com.university.lele.score.model.RecordModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface RecordService {
    Result add(RecordModel recordModel);


    Result uploadImg(MultipartFile file);

    Result findRecordInfoById(String teacherId, String studentId);
}
