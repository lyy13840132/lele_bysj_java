package com.university.lele.data.service;

import com.university.lele.data.model.ControlDateXY;
import com.university.lele.data.model.FileUserModel;
import com.university.lele.global.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ControlService {
    Result upLoadControlData(FileUserModel fileUserModel);

    Result deleteData(int id);

    Result queryControlInfo(int id);

    Result updateControlDate(ControlDateXY controlDateXY);

    Result queryAllList(String schoolId);

    Result add(ControlDateXY controlDateXY);

    Result textFile(MultipartFile file);
}
