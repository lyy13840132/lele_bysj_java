package com.university.lele.score.api;

import com.university.lele.global.Result;
import com.university.lele.score.model.MutualEvaluationModel;
import com.university.lele.score.service.MutualEvaluationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 学生互评
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "mutualEvaluation")
public class MutualEvaluationAPI {

    @Autowired
    MutualEvaluationService mutualEvaluationService;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Result mutualEvaluation( @RequestBody MutualEvaluationModel evaluationModel){
        return mutualEvaluationService.mutualEvaluation(evaluationModel);
    }
    @RequestMapping(value = "/findById/{studentId}",method = RequestMethod.GET)
    public Result findMutualEvaluationInfo(@PathVariable(value = "studentId")String studentId){
        return mutualEvaluationService.findMutualInfo(studentId);
    }
    /**
     * 学生互评排名
     * @param teacherId
     * @param
     * @return
     */
    @RequestMapping(value = "/ranking",method = RequestMethod.GET)
    public Result collectStarNum(@RequestParam(value = "teacherId") String teacherId,
                                 @RequestParam(value = "groupName") int groupName){

        return mutualEvaluationService.studentRanking(teacherId,groupName);
    }
    @RequestMapping(value = "/all/{teacherId}",method = RequestMethod.GET)
    public Result allMutualInfos(@PathVariable(value = "teacherId")String teacherId){

        return mutualEvaluationService.allMutualInfos(teacherId);
    }

    @RequestMapping(value = "/lineGraphs",method = RequestMethod.GET)
    public Result sortMutual(@RequestParam(value = "teacherId") String teacherId){
        return mutualEvaluationService.sortMutual(teacherId);
    }

}
