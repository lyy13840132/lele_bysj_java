package com.university.lele.score.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

@Service
 public interface ScoreService {

    @Query
     void saveScoreServie();
}
