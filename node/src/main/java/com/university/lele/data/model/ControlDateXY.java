package com.university.lele.data.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ControlDateXY {
    private int id;
    private String name;
    private String x;
    private String y;
    private String z;
    private int state;//表示控制点状态，1表示完好，0表示破坏
    private String note;//备注信息
    private String schoolId;
}
