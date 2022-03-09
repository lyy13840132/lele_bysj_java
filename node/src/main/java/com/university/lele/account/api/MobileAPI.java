package com.university.lele.account.api;

import com.university.lele.account.model.UserModel;
import com.university.lele.account.service.StudentService;
import com.university.lele.account.service.TeacherService;
import com.university.lele.data.service.FileService;
import com.university.lele.enums.Code;
import com.university.lele.global.Result;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class MobileAPI {


    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;
    @Autowired
    FileService fileService;
    /**
     * 修改头像
     * @return
     */
    @PostMapping(value = "/upload/avatar")
    @ResponseBody
    public Result loadAvatar(UserModel userModel) throws Exception {
        return fileService.loadAvatar(userModel);
    }

    /**
     * 移动端更新数据的接口
     * @param userModel
     * @return
     */
    @RequestMapping(value = "/single_update",method = RequestMethod.POST)
    public Result singleUpdate(@RequestBody @Validated UserModel userModel){
      //  System.out.println(userModel);
        switch (userModel.getUserType()){
            case 2://教师端更新信息
                //上传用户头像

                if (userModel.getAvatar()!=null){
                    return teacherService.updateAvatar(userModel);
                }else if (userModel.getPhone()!=null){
                    return teacherService.setPhone(userModel);
                }else if (userModel.getPassword()!=null&userModel.getOldPassword()!=null){
                    return teacherService.setPassword(userModel);
                }else {
                    return Result.error(Code.INFO_ERR,"请填写完整信息！");
                }

            case 3://学生端更新信息
                //上传用户头像
                if (userModel.getAvatar()!=null){
                    return studentService.updateAvatar(userModel);
                }else if (userModel.getPhone()!=null){
                    return studentService.setPhone(userModel);
                }else if (userModel.getPassword()!=null&userModel.getOldPassword()!=null){
                    return studentService.setPassword(userModel);
                }else if (userModel.getLatitude()!=null&userModel.getLongitude()!=null){
                    return studentService.setLatAndLong(userModel);
                }else {
                    Result.error(Code.INFO_ERR,"请填写完整信息");
                }
            default:
                return Result.error(Code.USER_TYPE_ERR,"userType错误");
        }
    }


    @RequestMapping(value = "/loadingStation",method = RequestMethod.GET)
    public Result loadingStation(@RequestParam(value = "schoolId")String schoolId){
        return fileService.findStationListBySchoolId(schoolId);
    }

}
