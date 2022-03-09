package com.university.lele.siginIn.api;

import com.university.lele.global.Result;
import com.university.lele.siginIn.model.SignInModel;
import com.university.lele.siginIn.service.SignInService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "signIn")
public class SignInAPI {

    @Autowired
    SignInService signInService;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result signIn(@RequestBody @Validated SignInModel signInModel){

        return signInService.signIn(signInModel);
    }

    @RequestMapping(value = "/findById",method = RequestMethod.GET)
    public Result querySignInInfo(@RequestParam(value = "studentId")String studentId){
        return signInService.findSignInInfo(studentId);
    }

    @RequestMapping(value = "/Statistics/{teacherId}",method = RequestMethod.GET)
    public Result statisticsMark(@PathVariable(value = "teacherId")String teacherId){
        return signInService.statisticsSignIn(teacherId);
    }
    @RequestMapping(value = "/all/{teacherId}",method = RequestMethod.GET)
    public Result allSignIn(@PathVariable(value = "teacherId")String teacherId){
        return signInService.allSign(teacherId);
    }
}
