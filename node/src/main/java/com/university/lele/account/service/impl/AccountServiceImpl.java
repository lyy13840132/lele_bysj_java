package com.university.lele.account.service.impl;

import com.university.lele.account.entity.CreateASchool;
import com.university.lele.account.entity.Studentinfo;
import com.university.lele.account.model.CreateSchoolModel;
import com.university.lele.account.repositity.CreateASchoolRepository;
import com.university.lele.account.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    @Autowired
    CreateASchoolRepository aScoolRepository;
    @Override
    public List<Studentinfo> allRegisterStudent(List<Studentinfo> studentinfos) {
        return null;
    }

    @Override
    public boolean addSchool(CreateSchoolModel createSchoolModel) {
        CreateASchool createAScoo=new CreateASchool();
        createAScoo.setName(createSchoolModel.getName());
        //createAScoo.setPracticeName(createSchoolModel.getPracticeName());
        List<CreateASchool> aScoolRepositoryAll = aScoolRepository.findAll();
        List<CreateASchool> list=new ArrayList<>();
        for (CreateASchool c:aScoolRepositoryAll) {
            if (c.getName().equals(createSchoolModel.getName())){
                list.add(c);
            }
        }
        if (list.size()>0){
            return false;
        } else {
            aScoolRepository.save(createAScoo);
            return true;
        }


    }
}
