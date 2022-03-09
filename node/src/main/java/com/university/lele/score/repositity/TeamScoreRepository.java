package com.university.lele.score.repositity;

import com.university.lele.score.entity.TeamScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TeamScoreRepository extends JpaRepository<TeamScore, Integer>, JpaSpecificationExecutor<TeamScore> {




}