package com.university.lele.account.service.impl;

import com.university.lele.account.entity.CreateASchool;
import com.university.lele.account.entity.SchoolConfiguration;
import com.university.lele.account.entity.TeacherConfiguration;
import com.university.lele.account.model.LoginModel;
import com.university.lele.account.model.CreateSchoolModel;
import com.university.lele.account.model.SchoolConfigModel;
import com.university.lele.account.model.SettingUserModel;
import com.university.lele.account.repositity.CreateASchoolRepository;
import com.university.lele.account.repositity.SchoolConfigurationRepository;
import com.university.lele.account.service.SchoolService;
import com.university.lele.enums.Code;
import com.university.lele.global.MyKEY;
import com.university.lele.global.Result;
import com.university.lele.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class SchoolServiceImpl implements SchoolService {
    @Autowired
    CreateASchoolRepository aScoolRepository;

    @Autowired
    SchoolConfigurationRepository configurationRepository;
    @Override
    public Result add(CreateSchoolModel createSchoolModel) {
        CreateASchool createAScoo = new CreateASchool();
        if (createSchoolModel.getUsername().equals("admin")){
            Result.error(Code.SCHOOL_ADDACCOUNT_ERR,"账号名称不合法！");
        }else {
            createAScoo.setUsername(createSchoolModel.getUsername());
        }
        createAScoo.setPassword(MyKEY.SCHOOL_PASSWORD);
        createAScoo.setName(createSchoolModel.getName());
        createAScoo.setId(Utils.getUUID());
        createAScoo.setUserType(1);
        List<CreateASchool> aScoolRepositoryAll = aScoolRepository.findAll();
        List<CreateASchool> list = new ArrayList<>();
        if (aScoolRepositoryAll.size() > 0) {
            for (CreateASchool c : aScoolRepositoryAll) {
                if (c.getUsername().equals(createSchoolModel.getUsername())) {
                    list.add(c);
                }
            }
            if (list.size() > 0) {
                return Result.error(Code.CREATE_SCHOOL_ERR, "用户名重名！");
            } else {
                CreateASchool save = aScoolRepository.save(createAScoo);
                return Result.success("添加成功", save);
            }
        } else {
            CreateASchool save = aScoolRepository.save(createAScoo);
            return Result.success("添加成功", save);
        }


    }

    @Override
    public boolean delete(String schoolId) {
        CreateASchool model = aScoolRepository.findSchoolInfoBySchoolId(schoolId);
        if (model != null) {
            aScoolRepository.delete(model);
            return true;
        } else {
            return false;
        }


    }

    @Override
    public Result update(CreateSchoolModel createSchoolModel) {

        CreateASchool createAScool = aScoolRepository.findSchoolInfoBySchoolId(createSchoolModel.getId());
        if (createAScool != null) {
            createAScool.setName(createSchoolModel.getName());
            createAScool.setUsername(createSchoolModel.getUsername());
            return Result.success("数据更新成功", aScoolRepository.save(createAScool));

        } else {
            return Result.error(Code.UPDATE_ERR_NOEXIST, "数据不存在，更新失败！");
        }


    }

    @Override
    public Result findSchoolBySchoolId(String schoolId) {
        CreateASchool c = aScoolRepository.findSchoolInfoBySchoolId(schoolId);
        if (c != null) {
            return Result.success("查询成功", c);
        }else {

            return Result.error(Code.FIND_ERR, "查询数据失败");
        }
    }

    @Override
    public CreateSchoolModel findSchoolInfoBySchoolId(String schoolId) {
        return null;
    }

    @Override
    public Result queryAllSchoolInfo() {
        List<CreateASchool> aSchools = aScoolRepository.findAll();
        if (aSchools.size() > 0) {
            return Result.success("所有学校信息", aSchools);
        } else {

            return Result.success("没有数据");
        }
    }

    @Override
    public Result login(LoginModel loginModel) {
        CreateASchool school=aScoolRepository.findSchoolInfoByusername(loginModel.getUsername());
        System.out.println(school);
        if (school!=null){
            if (loginModel.getPassword().equals(school.getPassword())){
                return Result.success("登录成功",school);
            }else {
                return Result.error(Code.SCHOOL_LOGIN_ERR,"密码错误！");
            }

        } else {
            return Result.error(Code.SCHOOL_LOGIN_NO,"帐号输入错误或帐号不存在！");
        }
    }

    @Override
    public Result rePassWord(SettingUserModel passWordModel) {
        CreateASchool createASchool = aScoolRepository.findSchoolInfoByusername(passWordModel.getId());
        if (createASchool!=null){
                createASchool.setPassword(passWordModel.getPassword());
                aScoolRepository.save(createASchool);
                return Result.success("重置密码成功！",createASchool);



        }else {
            return Result.error(Code.SCHOOL_PASSWORD_NO,"设置失败，帐号不存在！");
        }
    }

    @Override
    public Result setConfig(SchoolConfigModel configModel) {

        return null;
    }

    @Override
    public Result setIsSwitch(SchoolConfigModel schoolConfigModel) {
        SchoolConfiguration config = configurationRepository.findConfigBySchoolId(schoolConfigModel.getSchoolId());
        if (config!=null){
            config.setAssessmentSwitch(schoolConfigModel.getAssessmentSwitch());
            configurationRepository.save(config);
            return Result.success("设置成功！");
        }else {
            SchoolConfiguration configuration=new SchoolConfiguration();
            configuration.setAssessmentSwitch(schoolConfigModel.getAssessmentSwitch());
            configuration.setSchoolId(schoolConfigModel.getSchoolId());
            configurationRepository.save(configuration);
            return Result.success("设置成功！");
        }
    }

    @Override
    public Result getConfig(String schoolId) {
        SchoolConfiguration configuration = configurationRepository.findConfigBySchoolId(schoolId);
        return configuration!=null?Result.success("查询成功！",configuration)
                :Result.success("没有设置信息");

    }

}
