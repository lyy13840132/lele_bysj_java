package com.university.lele.siginIn.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CollectSignInModel {
    private String username;
    private String name;
    private int grade;
    private boolean groupType;//YES or NO//YES表示是组长，NO表示不是组长
    private int groupName;
    private int normalStatus;
    private int lateStatus;
    private int normalField;
    private int lateField;
}
