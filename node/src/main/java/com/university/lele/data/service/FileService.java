package com.university.lele.data.service;

import com.university.lele.account.model.UserModel;
import com.university.lele.data.model.ExportExcelModel;
import com.university.lele.data.model.FileUserModel;
import com.university.lele.global.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public interface FileService {

    /**
     * 上传实习资料数据
     * @param model
     * @return
     */
    Result upLoadDateFile(FileUserModel model)throws Exception;


    /**
     * 更新头像
     */
    Result loadAvatar(UserModel userModel) throws Exception;

    Result parseExcel();

    Result findStationListBySchoolId(String schoolId);

    Result findMaterialBySchoolId(String schoolId);

    Result downLoadFile(String fileName);

     List<ExportExcelModel> writeExcel(String teacherId) throws Exception;
}
