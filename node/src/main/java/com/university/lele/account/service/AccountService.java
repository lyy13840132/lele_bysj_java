package com.university.lele.account.service;

import com.university.lele.account.entity.Studentinfo;
import com.university.lele.account.model.CreateSchoolModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {
    /**
     * 批量注册学生账号
     * @param studentinfos
     * @return
     */
     List<Studentinfo> allRegisterStudent(List<Studentinfo> studentinfos);

    /**
     * 添加学校信息
     * @param createAScool
     */
     boolean addSchool(CreateSchoolModel createAScool);
}
