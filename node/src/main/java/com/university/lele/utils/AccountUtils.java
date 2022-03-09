package com.university.lele.utils;


import com.university.lele.account.repositity.CreateASchoolRepository;
import com.university.lele.account.repositity.StudentinfoRepository;
import com.university.lele.score.repositity.MutualEvaluationInfoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class AccountUtils {

    @Autowired
     CreateASchoolRepository scoolRepository;
    @Autowired
    MutualEvaluationInfoRepository mutualEvaluationInfoRepository;
    @Autowired
     StudentinfoRepository studentinfoRepository;


}
