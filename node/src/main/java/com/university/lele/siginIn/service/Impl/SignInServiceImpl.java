package com.university.lele.siginIn.service.Impl;

import com.university.lele.account.entity.Studentinfo;
import com.university.lele.account.repositity.StudentinfoRepository;
import com.university.lele.enums.Code;
import com.university.lele.global.MyKEY;
import com.university.lele.global.Result;
import com.university.lele.siginIn.entity.SignIn;
import com.university.lele.siginIn.entity.SignInInfo;
import com.university.lele.siginIn.model.CollectSignInModel;
import com.university.lele.siginIn.model.RebackSignIn;
import com.university.lele.siginIn.model.SignInModel;
import com.university.lele.siginIn.repositity.SignInInfoRepository;
import com.university.lele.siginIn.repositity.SignInRepository;
import com.university.lele.siginIn.service.SignInService;
import com.university.lele.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SignInServiceImpl implements SignInService {
    @Autowired
    SignInRepository signInRepository;
    @Autowired
    SignInInfoRepository signInInfoRepository;

    @Autowired
    StudentinfoRepository studentinfoRepository;
    @Override
    public Result signIn(SignInModel signInModel) {

       SignIn signIn=signInRepository.findSignInByStudentId(signInModel.getStudentId());

       if (signIn==null){
           SignIn signIn1=new SignIn();
           String uuid = Utils.getUUID();
           signIn1.setId(uuid);

           signIn1.setStudentId(signInModel.getStudentId());

           if (signInModel.getStatus()== MyKEY.NORMAL_STATUS){
               //代表正常打卡
               signIn1.setNormalStatusAll(1);
               signIn1.setLateStatusAll(0);
               signIn1.setNormalFieldAll(0);
               signIn1.setLateFieldAll(0);
           }else if (signInModel.getStatus()==MyKEY.LATE_STATUS){
               //迟到打卡
               signIn1.setNormalStatusAll(0);
               signIn1.setLateStatusAll(1);
               signIn1.setNormalFieldAll(0);
               signIn1.setLateFieldAll(0);
           }else if (signInModel.getStatus()==MyKEY.NORMAI_FIELD){

               signIn1.setNormalStatusAll(0);
               signIn1.setLateStatusAll(0);
               signIn1.setNormalFieldAll(1);
               signIn1.setLateFieldAll(0);
           }else if (signInModel.getStatus()==MyKEY.LATE_FIELD){
               signIn1.setNormalStatusAll(0);
               signIn1.setLateStatusAll(0);
               signIn1.setNormalFieldAll(0);
               signIn1.setLateFieldAll(1);
           }
           //将签到信息封装
           SignInInfo signInInfo=new SignInInfo();
           signInInfo.setDate(signInModel.getDate());
           signInInfo.setAddress(signInModel.getAddress());
           signInInfo.setStatus(signInModel.getStatus());
           signInInfo.setStation(signInModel.getStation());
           signInInfo.setStudentId(signInModel.getStudentId());
           signInInfoRepository.save(signInInfo);
           signInRepository.save(signIn1);
           return Result.success("签到成功");
       }else {

           //将签到数据封装
           //signInInfo.setSignInId(uuid);
           SignInInfo signInInfo=new SignInInfo();
           signInInfo.setDate(signInModel.getDate());
           signInInfo.setAddress(signInModel.getAddress());
           signInInfo.setStation(signInModel.getStation());
           signInInfo.setStatus(signInModel.getStatus());
           signInInfo.setStudentId(signInModel.getStudentId());
           signInInfoRepository.save(signInInfo);
           int normalStatus = signIn.getNormalStatusAll();
           int lateStatus = signIn.getLateStatusAll();
           int normalFieldAll = signIn.getNormalFieldAll();
           int lateFieldAll = signIn.getLateFieldAll();
           signIn.setStudentId(signInModel.getStudentId());
           if (signInModel.getStatus()== MyKEY.NORMAL_STATUS){
               //代表正常打卡
               signIn.setNormalStatusAll(++normalStatus);
           }else if (signInModel.getStatus()==MyKEY.LATE_STATUS){
               //迟到打卡
               signIn.setLateStatusAll(++lateStatus);
           }else if (signInModel.getStatus()==MyKEY.NORMAI_FIELD){
               signIn.setNormalFieldAll(++normalFieldAll);
           }else if (signInModel.getStatus()==MyKEY.LATE_FIELD){
               signIn.setLateFieldAll(++lateFieldAll);
           }
           signInRepository.save(signIn);
           return Result.success("签到成功");
       }

    }

    @Override
    public Result findSignInInfo(String studentId) {
        SignIn signIn = signInRepository.findSignInByStudentId(studentId);
        List<SignInInfo> signInInfos = signInInfoRepository.findSignInInfoByStudent_Id(studentId);
        if (signIn!=null&signInInfos.size()>0){
            return Result.success("查询成功",new RebackSignIn(signIn,signInInfos));
        }else {
            return Result.success("无签到数据！");

        }
    }

    @Override
    public Result statisticsSignIn(String teacherId) {
        List<Studentinfo> studentinfos = studentinfoRepository.queryStudentInfoByTeacherId(teacherId);
        List<CollectSignInModel> collectSignInModels=new ArrayList<>();
        for (Studentinfo s:studentinfos){
            CollectSignInModel collectSignInModel=new CollectSignInModel();
            SignIn signIn = signInRepository.findSignInByStudentId(s.getId());
            if (signIn!=null){
                collectSignInModel.setNormalStatus(signIn.getNormalStatusAll());
                collectSignInModel.setLateStatus(signIn.getLateStatusAll());
                collectSignInModel.setNormalField(signIn.getNormalFieldAll());
                collectSignInModel.setLateField(signIn.getLateFieldAll());
            }else {
                collectSignInModel.setNormalStatus(0);
                collectSignInModel.setLateStatus(0);
                collectSignInModel.setNormalField(0);
                collectSignInModel.setLateField(0);
            }
            collectSignInModel.setUsername(s.getUsername());
            collectSignInModel.setName(s.getName());
            collectSignInModel.setGrade(s.getGrade());
            collectSignInModel.setGroupType(s.getGroupType()==0?true:false);
            collectSignInModel.setGroupName(s.getGroupName());
            collectSignInModels.add(collectSignInModel);
        }
        return Result.success("统计结果为：",collectSignInModels);
    }

    @Override
    public Result allSign(String teacherId) {
        List<Studentinfo> studentinfos = studentinfoRepository.queryStudentInfoByTeacherId(teacherId);
        List<SignIn> signIns=new ArrayList<>();
        for (Studentinfo s:studentinfos){
            SignIn signIn = signInRepository.findSignInByStudentId(s.getId());
            if (signIn!=null){
                signIns.add(signIn);
            }
        }
        return signIns.size()>0?Result.success("查询成功",signIns):Result.success("无签到数据！");
    }

}
