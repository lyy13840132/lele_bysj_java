package com.university.lele.account.repositity;

import com.university.lele.account.entity.Studentinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentinfoRepository extends JpaRepository<Studentinfo, String>, JpaSpecificationExecutor<Studentinfo> {
    /**根据学生id查询学生信息**/
    @Query(value = "select *from studentinfo s where s.id =?1",nativeQuery = true)
    Studentinfo queryStudentinfoByStudent_id(String id);

    /**
     * 根据教师id查询学生列表信息
     * @param teacherId
     * @return
     */
    @Query(value = "select *from studentinfo s where s.teacher_id =?1",nativeQuery = true)
    List<Studentinfo> queryStudentInfoByTeacherId(String teacherId);

    /**
     * 通过小组id查询小组成员
     * @param group_name
     * @return
     */
    @Query(value = "select *from studentinfo s where s.group_name =?1",nativeQuery = true)
    List<Studentinfo> queryStudentInfoByGroup_Id(int group_name);


    @Query(value = "select *from studentinfo s where s.username =?1",nativeQuery = true)
    Studentinfo queryStudentInfoByUsername(String username);
    @Query(value = "select *from studentinfo s where s.teacher_id =?1 and s.group_name=?2",nativeQuery = true)
    List<Studentinfo> findGroupListByIdAndGroupName(String teacherId, int groupName);

}