package com.university.lele.score.repositity;

import com.university.lele.score.entity.TeacherMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherMarkRepository extends JpaRepository<TeacherMark, Integer>, JpaSpecificationExecutor<TeacherMark> {
    @Query(value = "select *from teacher_mark t where t.student_id =?1",nativeQuery = true)
    TeacherMark findTeacherMarkInfoById(String studentId);
    @Query(value = "select *from teacher_mark t where t.teacher_id =?1",nativeQuery = true)
    List<TeacherMark> findAllTeacherMarkInfoById(String teacherId);
}