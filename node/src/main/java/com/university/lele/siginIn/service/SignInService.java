package com.university.lele.siginIn.service;

import com.university.lele.global.Result;
import com.university.lele.siginIn.model.SignInModel;
import org.springframework.stereotype.Service;

@Service
public interface SignInService {

    Result signIn(SignInModel signInModel);

    Result findSignInInfo(String studentId);

    Result statisticsSignIn(String teacherId);

    Result allSign(String teacherId);
}
