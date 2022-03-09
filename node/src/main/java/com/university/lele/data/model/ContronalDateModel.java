package com.university.lele.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContronalDateModel implements Serializable {
    private int id;
    private String pointName;
    private String latitude;
    private String longitude;
    private String x;
    private String y;
    private String z;
    private String point_state;
    private String point_note;
}
