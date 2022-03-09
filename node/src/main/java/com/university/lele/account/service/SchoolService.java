package com.university.lele.account.service;

import com.university.lele.account.model.LoginModel;
import com.university.lele.account.model.CreateSchoolModel;
import com.university.lele.account.model.SchoolConfigModel;
import com.university.lele.account.model.SettingUserModel;
import com.university.lele.global.Result;
import org.springframework.stereotype.Service;

@Service
public interface SchoolService {


    /**
     * 添加学校信息
     * @param createAScool
     */
     Result add(CreateSchoolModel createAScool);

    /**
     * 删除学校信息
     * @param name
     * @return
     */
     boolean delete(String name);

    /**
     * 更新学校信息
     * @param createSchoolModel
     * @return
     */
     Result update(CreateSchoolModel createSchoolModel);

    /**
     * 查询学校信息通过学校id
     * @param schoolId
     * @return
     */
     Result findSchoolBySchoolId(String schoolId);
    /**
     * 查询学校信息通过学校id
     * @param schoolId
     * @return
     */
    CreateSchoolModel findSchoolInfoBySchoolId(String schoolId);

    Result queryAllSchoolInfo();

    Result login(LoginModel loginModel);

    Result rePassWord(SettingUserModel passWordModel);

    Result setConfig(SchoolConfigModel configModel);

    Result setIsSwitch(SchoolConfigModel schoolConfigModel);

    Result getConfig(String schoolId);
}
