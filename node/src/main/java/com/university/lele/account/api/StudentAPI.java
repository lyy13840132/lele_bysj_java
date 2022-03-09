package com.university.lele.account.api;


import com.university.lele.account.model.StudentLatAndLonModel;
import com.university.lele.account.model.StudentModel;
import com.university.lele.account.model.UserModel;
import com.university.lele.account.service.StudentService;
import com.university.lele.account.service.TeacherService;
import com.university.lele.global.Result;
import com.university.lele.utils.ExcelUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("student")
public class StudentAPI {

    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    /**
     * @param
     * @Description: 添加学生账号
     * @return:
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result createSchool(@RequestBody @Validated StudentModel studentModel) {

        return studentService.singAddStudentInfo(studentModel);

    }

    /**
     * 批量注册
     * @param
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/upload")
    @ResponseBody
    public Result addAllStudent( UserModel user)throws Exception{
        String postfix = ExcelUtil.getPostfix(user.getFile().getOriginalFilename());

        if (!"xlsx".equals(postfix) && !"xls".equals(postfix)) {
            return Result.error("导入失败，请选择正确的文件格式支持xlsx或xls");
        }
        return studentService.allRegisterStudent(user);

        //return
    }
    /**
     * 根据学生id删除
     * @param studentId
     * @return
     */
    @RequestMapping(value = "/delete/{studentId}",method = RequestMethod.DELETE)
    public Result deleteSchool(@PathVariable(value = "studentId") String studentId){
        return studentService.deleteStudentByStudent_id(studentId);
    }

    /**
     * 修改更新学生信息
     * @param studentModel
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result update(@RequestBody @Validated StudentModel studentModel){

        return studentService.update(studentModel);
    }

    /**
     * 根据学生id查询学生信息
     * @param studentId
     * @return
     *
     */
    @RequestMapping(value = "/findById/{studentId}",method = RequestMethod.GET)
    public Result findByDemandNo(@PathVariable(value = "studentId") String studentId) {
        return studentService.queryStudentInfoByStudentId(studentId);
    }
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Result contactList(@RequestParam(value = "teacherId")String teacherId,
                              @RequestParam(value = "groupName")int groupName){
       // System.out.println();
        if (groupName==0){
            return teacherService.getStudentList(teacherId);
        }else {
            return teacherService.getStudentList(teacherId,groupName);
        }
    }

    @RequestMapping(value = "/findLatAndLonById/{studentId}",method = RequestMethod.GET)
    public Result findLatAndLon(@PathVariable(value = "studentId")String studentId){
        return studentService.findLatAndLon(studentId);
    }
}
