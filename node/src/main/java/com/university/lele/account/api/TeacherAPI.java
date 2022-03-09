package com.university.lele.account.api;

import com.university.lele.account.model.SetTeacherModel;
import com.university.lele.account.model.SettingUserModel;
import com.university.lele.account.model.TeacherModel;
import com.university.lele.account.service.TeacherService;
import com.university.lele.global.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("teacher")
@RestController
public class TeacherAPI {

    @Autowired
    TeacherService service;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result singAddTeacher(@RequestBody @Validated TeacherModel teacherModel){
        return service.singAddTeacher(teacherModel);
    }
    @RequestMapping(value = "/delete/{teacher_id}",method = RequestMethod.DELETE)
    public Result deleteTeacherInfo(@PathVariable (value = "teacher_id") String teacher_id){
        return service.deleteTeacher(teacher_id);
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public Result updateTeacher(@RequestBody @Validated TeacherModel teacherModel){
        return service.updateTeacher(teacherModel);
    }

    /**
     * 通过教师id查询教师个人信息
     * @param teacherId
     * @return
     */
    @RequestMapping(value = "/findById/{teacherId}",method = RequestMethod.GET)
    public Result queryTeacherInfoByTeacherId(@PathVariable(value = "teacherId") String teacherId){
       return service.queryUserTeacherInfo(teacherId);
    }
    /**
     * 查询教师信息学
     * @param schoolId
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Result queryAllTeacherList(@RequestParam(value = "schoolId")String schoolId ){
        return service.queryTeacherList(schoolId);
    }

    @RequestMapping(value = "/set",method = RequestMethod.POST)
    public Result setSignIn(@RequestBody @Validated SetTeacherModel setTeacherModel){
        if(!"null".equals(String.valueOf(setTeacherModel.getScoreShowSwitch()))){
            service.setIsSwitch(setTeacherModel);
        }
        return service.setSignIn(setTeacherModel);
    }
    @RequestMapping(value = "/findBySignInSet/{teacherId}",method = RequestMethod.GET)
    public Result findSignInSet(@PathVariable(value = "teacherId") String teacherId){
        return service.findSignInSet(teacherId);
    }
}
