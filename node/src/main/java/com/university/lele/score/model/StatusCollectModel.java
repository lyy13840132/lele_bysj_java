package com.university.lele.score.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatusCollectModel {
    //评分等级优秀、良好、中等、及格、不及格
    private String teacherName;//指导教师
    private String username;
    /*
    优秀
     */
    private int excellent;//优秀
    private int good;//良好
    private int medium;//中等
    private int passes;//及格
    private int no_passes;//不及格

}
