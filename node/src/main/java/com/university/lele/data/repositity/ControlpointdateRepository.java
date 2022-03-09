package com.university.lele.data.repositity;

import com.university.lele.data.entity.Controlpointdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ControlpointdateRepository extends JpaRepository<Controlpointdate, Integer>, JpaSpecificationExecutor<Controlpointdate> {

    /**
     * 通过控制点名查询点数据
     * @param pointName
     * @return
     */
    @Query(value = "select * from controlpointdate c where c.name =?1",nativeQuery = true)
    Controlpointdate findPointDateByPointName(@Param("point_name") String pointName);

    @Query(value = "select * from controlpointdate c where c.id =?1",nativeQuery = true)
    Controlpointdate queryControlDataInfo(int id);

    @Query(value = "select * from controlpointdate c where c.school_id =?1",nativeQuery = true)
    List<Controlpointdate> findCortolDataListBySchoolId(String school_id);
}