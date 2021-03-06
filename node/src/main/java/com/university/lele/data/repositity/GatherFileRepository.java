package com.university.lele.data.repositity;

import com.university.lele.data.entity.GatherFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GatherFileRepository extends JpaRepository<GatherFile, Integer>, JpaSpecificationExecutor<GatherFile> {
    @Query(value = "select * from gather_file g where g.school_id =?1",nativeQuery = true)
    List<GatherFile> findMaterailBySchoolId(String schoolId);
}