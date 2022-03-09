package com.university.lele.account.repositity;

import com.university.lele.account.entity.SchoolConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface SchoolConfigurationRepository extends JpaRepository<SchoolConfiguration, Integer>, JpaSpecificationExecutor<SchoolConfiguration> {

    @Query(value = "select *from school_configuration s where s.school_id =?1",nativeQuery = true)
    SchoolConfiguration findConfigBySchoolId(String schoolId);
}