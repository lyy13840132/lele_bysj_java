package com.university.lele.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ControlDateLonAndLat {
    private String name;
    private String latitude;
    private String longitude;
}
