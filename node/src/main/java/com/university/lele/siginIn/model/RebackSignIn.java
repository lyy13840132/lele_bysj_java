package com.university.lele.siginIn.model;

import com.university.lele.siginIn.entity.SignIn;
import com.university.lele.siginIn.entity.SignInInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RebackSignIn {

    private SignIn signIn;
    //private SignInInfo signInInfo;
    private List<SignInInfo> signInInfos;
}
