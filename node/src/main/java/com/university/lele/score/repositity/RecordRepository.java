package com.university.lele.score.repositity;

import com.university.lele.score.entity.Record;
import com.university.lele.score.model.RecordModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, String>, JpaSpecificationExecutor<Record> {
    @Query(value = "select *from record r where r.student_id =?1",nativeQuery = true)
    Record findRecordInfoByStudentId(String studentId);
    @Query(value = "select *from record r where r.teacher_id =?1 and r.student_id=?2",nativeQuery = true)
    List<Record> findRecordInfoByStudentIdAndTeacherId(String teacherId, String studentId);
    @Query(value = "select *from record r where r.teacher_id =?1",nativeQuery = true)
    Record findRecordInfoByTeacherId(String teacherId);
}