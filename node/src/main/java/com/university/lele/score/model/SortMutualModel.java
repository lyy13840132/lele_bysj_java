package com.university.lele.score.model;


import com.university.lele.score.entity.MutualEvaluation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SortMutualModel implements Comparable<SortMutualModel>{
    private String username;
    private String name;
    private Integer starNum;
    private Double avgStar;
    private Integer groupName;

    private Integer groupType;



    @Override
    public int compareTo(SortMutualModel o) {

        return this.avgStar.compareTo(o.getAvgStar());
    }
}
