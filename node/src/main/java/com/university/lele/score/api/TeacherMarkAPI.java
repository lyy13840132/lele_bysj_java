package com.university.lele.score.api;


import com.university.lele.global.Result;
import com.university.lele.score.model.TeacherMarkModel;
import com.university.lele.score.service.MarkService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "mark")
public class TeacherMarkAPI {

    @Autowired
    MarkService markService;
    @RequestMapping(value = "/addAndModify",method = RequestMethod.POST)
    public Result teacherMark(@RequestBody @Validated TeacherMarkModel teacherMarkModel){
        return markService.add(teacherMarkModel);
    }
    @RequestMapping(value = "/findById/{studentId}",method = RequestMethod.GET)
    public Result findMarkInfoById(@PathVariable(value = "studentId")String studentId){
        return markService.findMarkInfoById(studentId);
    }


    @RequestMapping(value = "/FindAllById",method = RequestMethod.GET)
    public Result findAllMarkInfoById(@RequestParam(value = "teacherId")String teacherId){
        return markService.findAllMarkInfoById(teacherId);
    }

    @RequestMapping(value = "/Statistics/{teacherId}",method = RequestMethod.GET)
    public Result statisticsMark(@PathVariable(value = "teacherId")String teacherId){
        return markService.statisticsMark(teacherId);
    }

}
