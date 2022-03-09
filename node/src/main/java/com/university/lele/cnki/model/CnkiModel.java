package com.university.lele.cnki.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CnkiModel {
    private String username;
    private String name;
    private String content;

}
