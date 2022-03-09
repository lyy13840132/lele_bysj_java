package com.university.lele.cnki.service;

import com.university.lele.global.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface CnkiService {

    Result cnkiFiles(MultipartFile[] files,double simThre,String teacherId);

    Result finaHighPlagiarismByUsername(String studentId);

    Result deleteAll(String teacherId);


    Result findCnkiInfo(String teacherId, String username, Integer crib);

    Result statisticsData(String teacherId);
}
