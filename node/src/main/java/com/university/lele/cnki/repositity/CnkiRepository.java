package com.university.lele.cnki.repositity;

import com.university.lele.cnki.entity.Cnki;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CnkiRepository extends JpaRepository<Cnki, String>, JpaSpecificationExecutor<Cnki> {
    @Query(value = "select *from cnki c where c.crib =?1",nativeQuery = true)
    List<Cnki> findPlagiarism(int i);
    @Query(value = "select *from cnki c where c.teacher_id =?1",nativeQuery = true)
    List<Cnki> findAllCnkiInfoListByTeacherId(String teacherId);
    @Query(value = "select *from cnki c where c.teacher_id=?1 and c.username1 =?2",nativeQuery = true)
    List<Cnki> findCnkiInfoListByUsername(String teacherId,String username);
    @Query(value = "select *from cnki c where c.teacher_id=?1 and c.username2 =?2",nativeQuery = true)
    List<Cnki> findCnkiInfoListByUsernameSim(String teacherId,String username);

    @Query(value = "select *from cnki c where c.teacher_id=?1 and c.crib =?2",nativeQuery = true)
    List<Cnki> findCnkiInfoByCrib(String teacherId,int crib);
    @Query(value = "select *from cnki c where c.teacher_id=?1 and c.username1=?2 and c.crib =?3",nativeQuery = true)
    List<Cnki> findCnkiInfoByUserNameAndCrib(String teacherId, String username, int crib);

    @Query(value = "select *from cnki c where c.teacher_id=?1 and c.username2=?2 and c.crib =?3",nativeQuery = true)
    List<Cnki> findCnkiInfoByUserNameSimAndCrib(String teacherId, String username, int crib);

}