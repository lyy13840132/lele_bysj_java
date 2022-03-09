package com.university.lele.score.repositity;

import com.university.lele.score.entity.MutualEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MutualEvaluationRepository extends JpaRepository<MutualEvaluation, Integer>, JpaSpecificationExecutor<MutualEvaluation> {
    @Query(value = "select *from mutual_evaluation m where m.student_id =?1",nativeQuery = true)
    MutualEvaluation findStudentMutualInfoById(String studentId);
    @Query(value = "select *from mutual_evaluation m where m.teacher_id =?1",nativeQuery = true)
    List<MutualEvaluation> findStudentMutualInfosByTeacherId(String teacherId);
}