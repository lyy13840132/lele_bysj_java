package com.university.lele.cnki.repositity;

import com.university.lele.cnki.entity.CnkiHighSim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CnkiHighSimRepository extends JpaRepository<CnkiHighSim, Integer>, JpaSpecificationExecutor<CnkiHighSim> {
    @Query(value = "select *from cnki_high_sim c where c.username=?1",nativeQuery = true)
    List<CnkiHighSim> findCnkiInfoListByUsername(String username);
    @Query(value = "select *from cnki_high_sim c where c.username_sim=?1",nativeQuery = true)
    List<CnkiHighSim> findCnkiInfoListByUsernameSim(String username);
}