package com.university.lele.tencentIm.api;

import com.university.lele.global.Result;
import com.university.lele.tencentIm.service.UserSigService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
public class UserSigAPI {

    @Resource
    UserSigService userSigService;


    @RequestMapping(value = "getUserSig",method = RequestMethod.GET)
    public Result getUserSig(@RequestParam(value = "username") String username){
        return userSigService.generateUserSig(username);
    }
}
