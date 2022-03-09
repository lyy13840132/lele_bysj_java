package com.university.lele.account.api;

import com.university.lele.account.model.LoginModel;
import com.university.lele.account.repositity.TeacherinfoRepository;
import com.university.lele.account.service.SchoolService;
import com.university.lele.account.service.StudentService;
import com.university.lele.account.service.TeacherService;
import com.university.lele.enums.Code;
import com.university.lele.global.MyKEY;
import com.university.lele.global.Result;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "account")
public class AccountAPI {


    @Autowired
    SchoolService schoolService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;
    /**
     * 超级管理员登录
     * @param loginModel
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody @Validated LoginModel loginModel) {
        //系统默认超级管理员帐号：admin，密码：123456
        switch (loginModel.getUserType()) {
            case 0:
                //超级管理员登录
                return adminLogin(loginModel);
            case 1:
                //登录学校帐号
                return schoolService.login(loginModel);

            case 2:
                //登录教师帐号
               return teacherService.teacherLogin(loginModel);

            case 3:
                return studentService.studentLogin(loginModel);
            default:
                return Result.error(Code.USER_TYPE_ERR,"帐号错误！");
        }


    }

    private Result adminLogin(@Validated @RequestBody LoginModel loginModel) {
        if (loginModel.getUsername().isEmpty() || loginModel.getPassword().isEmpty()) {
            return new Result(Code.LOGIN_ERR_ISEMPTY, "帐号或密码不能为空", null);
        } else if (!loginModel.getUsername().equals(MyKEY.ADMIN)) {

            return new Result(Code.LOGIN_ERR_USER, "帐号错误", null);

        } else if (!loginModel.getPassword().equals(MyKEY.PASSWORD)) {
            return new Result(Code.LOGIN_ERR_PASSWORD, "密码错误", null);
        } else {
            return new Result(Code.OK, "登录成功", loginModel);
        }
    }
}
