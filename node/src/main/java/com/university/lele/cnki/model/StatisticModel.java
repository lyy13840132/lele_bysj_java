package com.university.lele.cnki.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatisticModel {
   private Integer highSimNum;
   private Integer qualifiedNum;
   private Integer cnkiNum;
   private Integer allNum;
}
