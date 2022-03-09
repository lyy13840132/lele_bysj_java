package com.university.lele.siginIn.repositity;

import com.university.lele.siginIn.entity.SignInInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SignInInfoRepository extends JpaRepository<SignInInfo, Void>, JpaSpecificationExecutor<SignInInfo> {

    @Query(value = "select * from sign_in_info s where s.student_id =?1", nativeQuery = true)
    List<SignInInfo> findSignInInfoByStudent_Id(String student_id);
}