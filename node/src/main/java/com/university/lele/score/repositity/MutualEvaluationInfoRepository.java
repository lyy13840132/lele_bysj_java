package com.university.lele.score.repositity;

import com.university.lele.score.entity.MutualEvaluationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MutualEvaluationInfoRepository extends JpaRepository<MutualEvaluationInfo, Integer>, JpaSpecificationExecutor<MutualEvaluationInfo> {
    @Query(value = "select *from mutual_evaluation_info m where m.teacher_id =?1 and m.group_name=?2",nativeQuery = true)
    List<MutualEvaluationInfo> findMutualInfoByTeacherIdAndGroupName(String teacherId, int groupName);
    @Query(value = "select *from mutual_evaluation_info m where m.teacher_id =?1",nativeQuery = true)
    List<MutualEvaluationInfo> findMutualInfoByTeacher_id(String teacherId);
}