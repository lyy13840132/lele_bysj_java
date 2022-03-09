package com.university.lele.account.service;

import com.university.lele.account.model.*;
import com.university.lele.global.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface StudentService {



    /**
     * 添加学生帐号/.......
     * @param studentModel
     * @return
     */
    Result singAddStudentInfo(StudentModel studentModel);
    /**
     * 学生帐号登录
     * @return
     */
    Result studentLogin(LoginModel loginModel);



    /**
     * 批量注册学生账号
     * @return
     */
    Result allRegisterStudent(UserModel user) throws Exception;


    Result queryStudentInfoByStudentId(String student_id);

    Result deleteStudentByStudent_id(String student_id);

    Result update(StudentModel studentModel);
    Result queryGroupInfoByGroupName(int groupName);



    Result reSettingPwd(SettingUserModel loginModel);

    Result setPhone(UserModel userModel);



    Result updateAvatar(UserModel userModel);

    Result setPassword(UserModel userModel);

    Result setLatAndLong(UserModel userModel);

    Result findLatAndLon(String studentId);
}
