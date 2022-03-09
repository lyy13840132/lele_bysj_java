package com.university.lele.account.api;

import com.university.lele.account.model.CreateSchoolModel;
import com.university.lele.account.model.SchoolConfigModel;
import com.university.lele.account.model.SettingUserModel;
import com.university.lele.account.service.SchoolService;
import com.university.lele.account.service.TeacherService;
import com.university.lele.enums.Code;
import com.university.lele.global.Result;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "school")
public class SchoolAPI {

    @Autowired
    SchoolService schoolService;
    @Autowired
    TeacherService teacherService;
    /**
     * @param
     * @Description: 创建学校
     * @return: 需求参数vo
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result createSchool(@RequestBody @Validated CreateSchoolModel createAScool) {
        return schoolService.add(createAScool);
    }
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public Result deleteSchool(@PathVariable(value = "id") String id){
        if (schoolService.delete(id)){
            return Result.success("删除成功");
        }else {
            return Result.error(Code.SCHOOL_DELETE_ERR,"删除失败");
        }
    }
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result updata(@RequestBody @Validated CreateSchoolModel createSchoolModel){
        return schoolService.update(createSchoolModel);
    }

    /**
     根据学校id查询学校信息
     */
    @RequestMapping(value = "/findSchoolInfo/{id}",method = RequestMethod.GET)
    public Result findByDemandNo(@PathVariable(value = "id") String id) {
        return schoolService.findSchoolBySchoolId(id);
    }

    /**
     * 返回学校所有信息
     * @return
     */
    @RequestMapping(value = "/school_list",method = RequestMethod.GET)
    public Result querySchoolList(){
        return schoolService.queryAllSchoolInfo();
    }
    @RequestMapping(value = "/allTeacherList/{id}",method = RequestMethod.GET)
    public Result queryListTeacherInfo(@PathVariable(value = "id") String id){
        return teacherService.queryTeacherList(id);
    }

    @RequestMapping(value = "rePassWord",method = RequestMethod.POST)
    public Result rePassWord(@RequestBody @Validated SettingUserModel passWordModel){
        return schoolService.rePassWord(passWordModel);
    }

    @RequestMapping(value = "/setConfig",method = RequestMethod.POST)
    public Result setConfig(@RequestBody @Validated SchoolConfigModel configModel){
        if(!"null".equals(String.valueOf(configModel.getAssessmentSwitch()))){
            return schoolService.setIsSwitch(configModel);
        }
        return Result.error(Code.CONFIG_SWITCH_NO,"开关参数不能为空！");
    }
    @RequestMapping(value = "/getConfig/{schoolId}",method = RequestMethod.GET)
    public Result getConfig(@PathVariable(value = "schoolId")String schoolId){
        return schoolService.getConfig(schoolId);
    }

}
