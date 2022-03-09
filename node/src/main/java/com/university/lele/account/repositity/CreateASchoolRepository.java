package com.university.lele.account.repositity;

import com.university.lele.account.entity.CreateASchool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface CreateASchoolRepository extends JpaRepository<CreateASchool, String>, JpaSpecificationExecutor<CreateASchool> {

    @Query(value = "select *from create_a_school s where s.username =?1",nativeQuery = true)
    CreateASchool findSchoolInfoByusername(String username);
    @Query(value = "select *from create_a_school s where s.id =?1",nativeQuery = true)
    CreateASchool findSchoolInfoBySchoolId(String id);
}