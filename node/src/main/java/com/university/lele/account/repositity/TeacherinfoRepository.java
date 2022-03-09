package com.university.lele.account.repositity;

import com.university.lele.account.entity.Teacherinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherinfoRepository extends JpaRepository<Teacherinfo, String>, JpaSpecificationExecutor<Teacherinfo> {
    @Query(value = "select *from teacherinfo t where t.id =?1",nativeQuery = true)
    Teacherinfo findTeacherByTeacherId(String id);
    @Query(value = "select *from teacherinfo t where t.username =?1",nativeQuery = true)
    Teacherinfo queryTeacherInfoByUsernName(String username);


    @Query(value = "select *from teacherinfo t where t.school_id =?1",nativeQuery = true)
    List<Teacherinfo> queryAllTeacherList(String school_id);
}